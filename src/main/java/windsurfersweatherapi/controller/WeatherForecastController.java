package windsurfersweatherapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather Forecast API")
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
  @Tag(name = "Weather Forecast API")
  @Operation(summary = "Get the best windsurfing location",
      description = "Returns the best windsurfing weather forecast or null "
          + "if all forecast are not suitable for surfing")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Data received successfully"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public WeatherForecastDto getBestWindsurfersWeatherForecast(
      @Parameter(
          description = "The date must not exceed the next 16 days "
              + "and should have a format YYYY-MM-DD",
          example = "2023-11-11",
          required = true)
      @RequestParam Optional<String> date) {
    LocalDate localDate = validator.validateAndParseDate(date);
    WeatherForecast weatherForecast = weatherForecastService.getBestWindsurfersForecast(localDate);
    return mapper.convertToWeatherForecastsDto(weatherForecast);
  }
}
