package windsurfersweatherapi.client;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.exception.CustomRetryExhaustedException;
import windsurfersweatherapi.exception.WeatherBitForecastApiClientException;
import windsurfersweatherapi.exception.WeatherBitForecastApiServerException;

/**
 * Client for interaction with <a href="https://www.weatherbit.io/">https://www.weatherbit.io/</a>.
 */
@Log4j2
@AllArgsConstructor
@Component
public class WeatherBitForecastApiClient {

  @Value("${app.weatherbit.api_key}")
  private String apiKey;
  @Value("${app.weatherbit.maximumRetries:3}")
  private int maxAttempts;
  @Value("${retryDelayInSeconds:3}")
  private int retryDelayInSeconds;
  static final String DAYS = "16";
  static final String UNITS = "M";
  static final String LANG = "en";
  static final String CITY = "city";
  static final String COUNTRY = "country";
  static final String LAT = "lat";
  static final String LON = "lon";
  private final WebClient webClient;

  @Autowired
  public WeatherBitForecastApiClient(WebClient webClient) {
    this.webClient = webClient;
  }

  /**
   * Method takes location as an argument and fetches weather forecast for the next 16 days for
   * specified location.
   *
   * @param location - Location enum
   * @return JsonNode for correct location or null in case of null respond and not error status code
   */
  public JsonNode fetchForecasts(Location location) {
    JsonNode weatherBitForecastResponse = webClient.get()
        .uri(uriBuilder -> uriBuilder.queryParams(getParamsMap(location)).build()).retrieve()
        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(
            new WeatherBitForecastApiServerException(response.rawStatusCode())))
        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(
            new WeatherBitForecastApiClientException(response.rawStatusCode())))
        .bodyToMono(JsonNode.class).retryWhen(
            Retry.fixedDelay(maxAttempts, Duration.ofSeconds(retryDelayInSeconds))
                .filter(throwable -> throwable instanceof WeatherBitForecastApiServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                  WeatherBitForecastApiServerException e =
                      (WeatherBitForecastApiServerException) retrySignal.failure();
                  throw new CustomRetryExhaustedException(e.getStatusCode(), e);
                }))
        .block();
    if (weatherBitForecastResponse == null) {
      log.warn("Forecast for location '{}' is not found", location);
    }
    return weatherBitForecastResponse;
  }

  private MultiValueMap<String, String> getParamsMap(Location location) {
    MultiValueMap<String, String> params = getCommonParams();
    if (location.getCoordinates() != null) {
      params.add(LAT, location.getCoordinates().getLatitude().toString());
      params.add(LON, location.getCoordinates().getLongitude().toString());
    } else if (location.getCity() != null) {
      params.add(CITY, location.getCity());
      if (location.getCountry() != null) {
        params.add(COUNTRY, location.getCountry());
      }
    }
    return params;
  }

  private MultiValueMap<String, String> getCommonParams() {
    MultiValueMap<String, String> commonParams = new LinkedMultiValueMap<>();
    commonParams.add("key", apiKey);
    commonParams.add("lang", LANG);
    commonParams.add("units", UNITS);
    commonParams.add("days", DAYS);
    return commonParams;
  }
}
