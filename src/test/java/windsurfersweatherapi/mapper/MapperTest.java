package windsurfersweatherapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static windsurfersweatherapi.factory.WeatherBitForecastResponseFactory.pissouriWeatherBitForecastFullResponse;
import static windsurfersweatherapi.factory.WeatherBitForecastResponseFactory.pissouriWeatherBitForecastShortResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import windsurfersweatherapi.exception.JsonMappingException;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.LocationData;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

@SpringBootTest
class MapperTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final WeatherBitForecastResponseMapper mapper = new WeatherBitForecastResponseMapper();

  @Test
  @DisplayName("JsonNode should be mapped to WeatherData correctly")
  public void jsonShouldBeMappedCorrectly() throws JsonProcessingException {
    var jsonNode = objectMapper.readTree(pissouriWeatherBitForecastShortResponse);
    var actual = mapper.convertToWeatherForecasts(jsonNode);

    List<WeatherForecast> expected = List.of(
        new WeatherForecast(
            LocalDate.of(2023, Month.NOVEMBER, 4),
            new LocationData("Pissouri", "CY", new Coordinates(32.70132, 34.66942)),
            new WeatherData(21.1, 1.7)
        ),
        new WeatherForecast(
            LocalDate.of(2023, Month.NOVEMBER, 5),
            new LocationData("Pissouri", "CY", new Coordinates(32.70132, 34.66942)),
            new WeatherData(21.1, 1.7)
        )
    );

    assertThat(actual).hasSameElementsAs(expected);
  }

  @Test
  @DisplayName("JsonNode with 16 days data should not throw any exceptions")
  public void fullJsonShouldNotThrowAnyException() throws JsonProcessingException {
    var jsonNode = objectMapper.readTree(pissouriWeatherBitForecastFullResponse);

    assertThatCode(() -> mapper.convertToWeatherForecasts(jsonNode))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Mapper with null as argument should return an empty list")
  public void shouldReturnNull() {
    assertThat(mapper.convertToWeatherForecasts(null)).isEmpty();
  }

  @Test
  @DisplayName("Mapper should throw JsonMappingException in case of unsuitable json")
  public void shouldThrowException() {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(
            () -> mapper.convertToWeatherForecasts(
                objectMapper.readTree("{\"mockk\": \"mockk\"}")
            )
        );
  }
}