package windsurfersweatherapi.dto;

import java.util.Date;
import java.util.Map;
import lombok.Data;
import windsurfersweatherapi.model.Coordinates;

/**
 * Data transfer forecast object for controller.
 */
@Data
public class ForecastDto {
  Date date;
  String city;
  String country;
  Coordinates coordinates;
  Map<String, Double> weatherConditions;
}
