package windsurfersweatherapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Simple class contains longitude and latitude values.
 */
@JsonSerialize
public record Coordinates(
    Double longitude,
    Double latitude
) {

}
