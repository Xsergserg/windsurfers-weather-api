package windsurfersweatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.WeatherData;

/**
 * Data transfer forecast object for controller.
 */
@JsonSerialize
public record WeatherForecastDto(
    @Schema(description = "Forecast date", example = "2023-11-11", required = true)
    String date,

    @Schema(description = "Location name", example = "Pissouri", required = true)
    String city,

    @JsonProperty("country_code")
    @Schema(description = "Code of country", example = "CY", required = true)
    String countryCode,

    @Schema(description = "Location coordinates", required = true)
    Coordinates coordinates,

    @JsonProperty("weather_data")
    @Schema(description = "Weather data", required = true)
    WeatherData weatherData
) {

}
