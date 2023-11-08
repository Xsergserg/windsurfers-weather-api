package windsurfersweatherapi.exception;

/**
 * response validation exception.
 */
public class ResponseValidationException extends RuntimeException {

  public ResponseValidationException(String message) {
    super(message);
  }
}
