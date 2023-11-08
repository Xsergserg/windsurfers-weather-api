package windsurfersweatherapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import windsurfersweatherapi.dto.WeatherForecastDto;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

class WeatherForecastDtoMapperTest {

  private final WeatherForecastDtoMapper mapper = new WeatherForecastDtoMapper();

  @Test
  @DisplayName("Weather forecast should be mapped correctly")
  void shouldReturnCorrectDto() {
    var weatherForecast = WeatherForecast.create(
        LocalDate.of(2023, 11, 10),
        "City17",
        "BR",
        1.0,
        1.0,
        30.0,
        5.0
    );
    var expected = new WeatherForecastDto(
        "2023-11-10",
        "City17",
        "BR",
        new Coordinates(1.0, 1.0),
        new WeatherData(30.0, 5.0)
    );

    assertThat(mapper.convertToWeatherForecastsDto(weatherForecast)).isEqualTo(expected);
  }

  @Test
  @DisplayName("Weather forecast should be mapped correctly with null location")
  void shouldReturnCorrectDtoWithNullLocation() {
    var weatherForecast = new WeatherForecast(
        LocalDate.of(2023, 11, 10),
        null,
        new WeatherData(30.0, 5.0)
    );
    var expected = new WeatherForecastDto(
        "2023-11-10",
        null,
        null,
        null,
        new WeatherData(30.0, 5.0)
    );

    assertThat(mapper.convertToWeatherForecastsDto(weatherForecast)).isEqualTo(expected);
  }

  @Test
  @DisplayName("Null weather forecast should be mapped correctly")
  void shouldReturnCorrectNullDto() {
    assertThat(mapper.convertToWeatherForecastsDto(null)).isEqualTo(null);
  }
}

