package windsurfersweatherapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import windsurfersweatherapi.dto.ForecastDto;
import windsurfersweatherapi.service.WeatherForecastService;

/**
 * Controller for windsurfers weather forecast.
 */
@RestController
public class WeatherForecastController {
  private final WeatherForecastService weatherForecastService;
  private final ObjectMapper objectMapper;

  @Autowired
  public WeatherForecastController(
      WeatherForecastService weatherForecastService,
      ObjectMapper objectMapper
  ) {
    this.weatherForecastService = weatherForecastService;
    this.objectMapper = objectMapper;
  }

  @GetMapping(path = "/")
  public ForecastDto getBestWindsurfersWeatherForecast() {
    return new ForecastDto();
  }
}
