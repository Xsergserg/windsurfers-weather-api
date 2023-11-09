package windsurfersweatherapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class contains weather data.
 */
@JsonSerialize
public record WeatherData(
    @Schema(description = "Average day temperature, C", example = "25.0", required = true)
    Double averageTemperature,
    @Schema(description = "Average wind speed, m/s", example = "5.0", required = true)
    Double averageWindSpeed
) {

}
