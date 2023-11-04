package windsurfersweatherapi.model;

import lombok.Data;

/**
 * Class contains weather data.
 */
@Data
public class WeatherData {
  final Double averageTemperature;
  final Double averageWindSpeed;
}
