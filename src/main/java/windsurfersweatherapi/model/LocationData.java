package windsurfersweatherapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class contains location data.
 */
@AllArgsConstructor
@Getter
public class LocationData {
  final String city;
  final String countryCode;
  final Coordinates coordinates;
}
