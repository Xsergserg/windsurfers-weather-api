package windsurfersweatherapi.mapper;

import static java.util.Collections.emptyList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Component;
import windsurfersweatherapi.exception.JsonMappingException;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.LocationData;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Mapper for converting json response from WeatherBitForecastApi.
 */
@Component
public class WeatherBitForecastResponseMapper {

  /**
   * Method converts WeatherBitForecastApi response to list of WeatherForecast.
   */
  public List<WeatherForecast> convertToWeatherForecasts(JsonNode jsonNode)
      throws JsonMappingException {
    if (jsonNode == null) {
      return emptyList();
    }
    try {
      LocationData locationData = parseLocationFromJsonNode(jsonNode);
      ArrayNode dataNode = (ArrayNode) jsonNode.get("data");
      return StreamSupport.stream(dataNode.spliterator(), true)
          .map(node -> parseDailyForecast(locationData, node))
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new JsonMappingException(jsonNode, e);
    }
  }

  private WeatherForecast parseDailyForecast(LocationData locationData, JsonNode node) {
    return new WeatherForecast(
        LocalDate.parse(node.get("valid_date").asText()),
        locationData,
        new WeatherData(
            node.get("temp").asDouble(),
            node.get("wind_spd").asDouble()
        )
    );
  }

  private LocationData parseLocationFromJsonNode(JsonNode jsonNode) {
    return new LocationData(
        jsonNode.get("city_name").asText(),
        jsonNode.get("country_code").asText(),
        new Coordinates(
            jsonNode.get("lon").asDouble(),
            jsonNode.get("lat").asDouble()
        )
    );
  }
}
