package windsurfersweatherapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains weather data.
 */
@JsonSerialize
public record WeatherData(
    Double averageTemperature,
    Double averageWindSpeed
) {

}
