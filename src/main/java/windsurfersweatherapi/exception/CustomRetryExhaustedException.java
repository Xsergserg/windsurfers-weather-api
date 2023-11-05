package windsurfersweatherapi.exception;

/**
 * Exception in case of max retries amount attempted.
 */
public class CustomRetryExhaustedException extends RuntimeException {

  public CustomRetryExhaustedException(int statusCode, Exception e) {
    super(String.format("External Service failed to process after max retries. Status code: '%d'",
        statusCode), e);
  }
}
