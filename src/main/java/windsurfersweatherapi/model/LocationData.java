package windsurfersweatherapi.model;

import lombok.Data;

/**
 * Class contains location data.
 */
@Data
public class LocationData {

  final String city;
  final String countryCode;
  final Coordinates coordinates;
}
