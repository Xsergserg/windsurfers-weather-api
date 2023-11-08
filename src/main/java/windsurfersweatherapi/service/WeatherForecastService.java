package windsurfersweatherapi.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
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
      WeatherForecastServiceValidator validator) {
    this.mapper = mapper;
    this.weatherBitForecastApiClient = weatherBitForecastApiClient;
    this.validator = validator;
  }

  /**
   * Getting the best location for chosen date.
   *
   * @param date chosen date
   * @return WeatherForecast
   */
  public WeatherForecast getBestWindsurfersForecast(LocalDate date) {
    validator.validateDate(date);
    return Arrays.stream(Location.values()).parallel()
        .map(
            node -> mapper.convertToWeatherForecasts(
                weatherBitForecastApiClient.fetchForecasts(node)))
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .peek(validator::validateForecast)
        .filter(forecast -> {
          Double averageWindSpeed = forecast.weatherData().averageWindSpeed();
          Double averageTemp = forecast.weatherData().averageTemperature();
          return forecast.date().equals(date)
              && isSuitableWindSpeed(averageWindSpeed)
              && isSuitableTemp(averageTemp);
        })
        .max(Comparator.comparing(this::calculateForecastRate))
        .orElse(null);
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
