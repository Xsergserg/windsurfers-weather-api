package windsurfersweatherapi.model;

import java.util.Date;
import lombok.Data;
import windsurfersweatherapi.enums.Location;

/**
 * Weather forecast model.
 */
@Data
public class WeatherForecast {
  Date date;
  Location location;
  Double averageTemperature;
  Double averageWindSpeed;
}
