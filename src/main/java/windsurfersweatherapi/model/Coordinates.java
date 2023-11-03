package windsurfersweatherapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple class contains longitude and latitude values.
 */
@AllArgsConstructor
@Getter
public class Coordinates {
  final Double longitude;
  final Double latitude;
}
