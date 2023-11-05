package windsurfersweatherapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import windsurfersweatherapi.client.WeatherBitForecastApiClient;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Weather forecast service. Service accepts date as an argument, fetches forecast data with
 * WeatherBitForecastClient for each location in Location enum for the next 16 days and returns best
 * weather forecast for surfing or null if weather is not suitable for surfing. Suitable weather for
 * surfing: 5°C <= t <= 35°C  && 5m/s <= w <= 18m/s Best location is determined by the highest value
 * calculated from the  formula: w * 3 + t where t - temperature in °C and w - wind speed in m/s
 */
@Service
public class WeatherForecastService {

  private final ObjectMapper mapper;
  private final WeatherBitForecastApiClient weatherBitForecastApiClient;

  @Autowired
  public WeatherForecastService(
      ObjectMapper mapper,
      WeatherBitForecastApiClient weatherBitForecastApiClient
  ) {
    this.mapper = mapper;
    this.weatherBitForecastApiClient = weatherBitForecastApiClient;
  }

  WeatherForecast getBestWindsurfersForecast(Date date) {
    return null;
  }
}
