package windsurfersweatherapi.dto;

import java.time.LocalDate;
import lombok.Data;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.WeatherData;

/**
 * Data transfer forecast object for controller.
 */
@Data
public class ForecastDto {
  LocalDate date;
  String city;
  String country;
  Coordinates coordinates;
  WeatherData weatherData;
}
