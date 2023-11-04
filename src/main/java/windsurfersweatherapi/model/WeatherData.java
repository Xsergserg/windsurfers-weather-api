package windsurfersweatherapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class contains weather data.
 */
@AllArgsConstructor
@Getter
public class WeatherData {
  Double averageTemperature;
  Double averageWindSpeed;
}
