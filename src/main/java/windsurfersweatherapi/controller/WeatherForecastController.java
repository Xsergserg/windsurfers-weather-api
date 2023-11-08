package windsurfersweatherapi.controller;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import windsurfersweatherapi.controller.validator.WeatherForecastControllerValidator;
import windsurfersweatherapi.dto.WeatherForecastDto;
import windsurfersweatherapi.mapper.WeatherForecastDtoMapper;
import windsurfersweatherapi.model.WeatherForecast;
import windsurfersweatherapi.service.WeatherForecastService;

/**
 * Controller for windsurfers weather forecast.
 */
@RestController
@RequestMapping("weatherForecast")
public class WeatherForecastController {

  private final WeatherForecastService weatherForecastService;
  private final WeatherForecastControllerValidator validator;
  private final WeatherForecastDtoMapper mapper;

  /**
   * Constructor.
   */
  @Autowired
  public WeatherForecastController(
      WeatherForecastService weatherForecastService,
      WeatherForecastControllerValidator validator,
      WeatherForecastDtoMapper mapper) {
    this.weatherForecastService = weatherForecastService;
    this.validator = validator;
    this.mapper = mapper;
  }

  /**
   * Path for getting the best location for chosen date.
   *
   * @param date requested date
   * @return WeatherForecastDto
   */
  @GetMapping(path = "/bestForWindsurfing")
  public WeatherForecastDto getBestWindsurfersWeatherForecast(@RequestParam Optional<String> date) {
    LocalDate localDate = validator.validateAndParseDate(date);
    WeatherForecast weatherForecast = weatherForecastService.getBestWindsurfersForecast(localDate);
    return mapper.convertToWeatherForecastsDto(weatherForecast);
  }
}
