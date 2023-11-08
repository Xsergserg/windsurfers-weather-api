package windsurfersweatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.WeatherData;

/**
 * Data transfer forecast object for controller.
 */
@JsonSerialize
public record WeatherForecastDto(
    String date,
    String city,
    @JsonProperty("country_code")
    String countryCode,
    Coordinates coordinates,
    @JsonProperty("weather_data")
    WeatherData weatherData
) {

}
