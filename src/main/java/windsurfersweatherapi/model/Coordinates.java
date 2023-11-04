package windsurfersweatherapi.model;

import lombok.Data;

/**
 * Simple class contains longitude and latitude values.
 */
@Data
public class Coordinates {
  final Double longitude;
  final Double latitude;
}
