package windsurfersweatherapi.model;

/**
 * Class contains location data.
 */
public record LocationData(
    String city,
    String countryCode,
    Coordinates coordinates
) {

}
