package windsurfersweatherapi.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Weather forecast model.
 */
@Data
@AllArgsConstructor
public class WeatherForecast {

  LocalDate date;
  LocationData locationData;
  WeatherData weatherData;
}
