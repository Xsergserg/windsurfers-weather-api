package windsurfersweatherapi.mapper;

import org.springframework.stereotype.Component;
import windsurfersweatherapi.dto.WeatherForecastDto;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Mapper for WeatherForecastDto.
 */
@Component
public class WeatherForecastDtoMapper {

  /**
   * Method converts WeatherForecast to WeatherForecastDto.
   */
  public WeatherForecastDto convertToWeatherForecastsDto(WeatherForecast weatherForecast) {
    if (weatherForecast == null) {
      return null;
    }
    var location = weatherForecast.locationData();
    return new WeatherForecastDto(
        weatherForecast.date().toString(),
        location != null ? location.city() : null,
        location != null ? location.countryCode() : null,
        location != null ? location.coordinates() : null,
        weatherForecast.weatherData()
    );
  }
}
