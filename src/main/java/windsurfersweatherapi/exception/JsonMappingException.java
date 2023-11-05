package windsurfersweatherapi.exception;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Exception in case of unsuccessful json parsing.
 */
public class JsonMappingException extends RuntimeException {

  public JsonMappingException(JsonNode jsonNode, Exception e) {
    super(String.format("Json '%s' can't be parsed correctly", jsonNode.toString()), e);
  }
}
