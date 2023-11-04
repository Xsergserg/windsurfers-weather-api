package windsurfersweatherapi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static windsurfersweatherapi.config.MapperFactory.pissouriWeatherBitForecastResponseJson;
import static windsurfersweatherapi.config.MapperFactory.readWeatherBitForecastResponseTree;
import windsurfersweatherapi.mapper.WeatherBitForecastResponseMapper;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.LocationData;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

@SpringBootTest
class MapperTest {
  @Test
  @DisplayName("JsonNode should map to WeatherData correctly")
  public void jsonShouldBeMappedCorrectly() throws JsonProcessingException {
    JsonNode jsonNode = readWeatherBitForecastResponseTree(pissouriWeatherBitForecastResponseJson());
    WeatherBitForecastResponseMapper mapper = new WeatherBitForecastResponseMapper();
    List<WeatherForecast> actual = mapper.convertToWeatherForecasts(jsonNode);
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
}