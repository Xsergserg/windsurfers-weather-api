package windsurfersweatherapi.service;

import static java.util.Collections.emptyList;

import com.github.benmanes.caffeine.cache.Cache;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import windsurfersweatherapi.client.WeatherBitForecastApiClient;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.mapper.WeatherBitForecastResponseMapper;
import windsurfersweatherapi.model.WeatherForecast;
import windsurfersweatherapi.service.validator.WeatherForecastServiceValidator;

/**
 * Weather forecast service. Service accepts date as an argument, fetches forecast data with
 * WeatherBitForecastClient for each location in Location enum for the next 16 days and returns best
 * weather forecast for surfing or null if weather is not suitable for surfing. Suitable weather for
 * surfing: 5°C <= t <= 35°C  && 5m/s <= w <= 18m/s Best location is determined by the highest value
 * calculated from the  formula: w * 3 + t where t - temperature in °C and w - wind speed in m/s
 */
@Service
public class WeatherForecastService {

  private final WeatherBitForecastResponseMapper mapper;
  private final WeatherBitForecastApiClient weatherBitForecastApiClient;
  private final WeatherForecastServiceValidator validator;
  private final Cache<LocalDate, List<WeatherForecast>> cache;

  /**
   * Constructor.
   *
   * @param mapper                      mapper for converting jsonNode to WeatherForecasts
   * @param weatherBitForecastApiClient API client
   * @param validator                   validator
   */
  @Autowired
  public WeatherForecastService(WeatherBitForecastResponseMapper mapper,
      WeatherBitForecastApiClient weatherBitForecastApiClient,
      WeatherForecastServiceValidator validator,
      Cache<LocalDate, List<WeatherForecast>> cache) {
    this.mapper = mapper;
    this.weatherBitForecastApiClient = weatherBitForecastApiClient;
    this.validator = validator;
    this.cache = cache;
  }

  /**
   * Getting the best location for chosen date.
   *
   * @param date chosen date
   * @return WeatherForecast
   */
  public WeatherForecast getBestWindsurfersForecast(LocalDate date) {
    validator.validateDate(date);
    checkCache(date);
    return cache.get(date, key -> emptyList())
        .stream()
        .filter(forecast ->
            isSuitableWindSpeed(forecast.weatherData().averageWindSpeed())
                && isSuitableTemp(forecast.weatherData().averageTemperature())
        )
        .max(Comparator.comparing(this::calculateForecastRate))
        .orElse(null);
  }

  private void checkCache(LocalDate date) {
    if (!cache.asMap().containsKey(date)) {
      cache.putAll(fetchForecasts());
    }
  }

  private Map<LocalDate, List<WeatherForecast>> fetchForecasts() {
    return Arrays.stream(Location.values()).parallel()
        .map(
            node -> mapper.convertToWeatherForecasts(
                weatherBitForecastApiClient.fetchForecasts(node)))
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .peek(validator::validateForecast)
        .collect(Collectors.groupingBy(WeatherForecast::date));
  }

  private Double calculateForecastRate(WeatherForecast forecast) {
    return forecast.weatherData().averageWindSpeed() * 3 + forecast.weatherData()
        .averageTemperature();
  }

  private Boolean isSuitableWindSpeed(Double windSpeed) {
    return windSpeed >= 5 && windSpeed <= 18;
  }

  private Boolean isSuitableTemp(Double temp) {
    return temp >= 5 && temp <= 35;
  }
}
