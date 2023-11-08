package windsurfersweatherapi.exception;

/**
 * Exception in case of max retries amount attempted.
 */
public class CustomRetryExhaustedException extends RuntimeException {

  /**
   * Exception constructor.
   */
  public CustomRetryExhaustedException(int statusCode, Exception e) {
    super(String.format("External Service failed to process after max retries. "
        + "External service statusCode code: '%d'. "
        + "Try to repeat request later", statusCode), e);
  }
}
