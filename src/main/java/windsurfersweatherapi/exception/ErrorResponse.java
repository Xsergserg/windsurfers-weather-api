package windsurfersweatherapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

/**
 * Response body for error.
 *
 * @param time         timestamp of error
 * @param statusCode   HTTP status
 * @param reasonPhrase HTTP status reason phrase
 * @param message      error message
 * @param path         request URI
 */
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public record ErrorResponse(
    String time,
    int statusCode,
    String reasonPhrase,
    String message,
    String path
) {

  public static ErrorResponse create(HttpStatus status, HttpServletRequest request) {
    return create(null, status, request);
  }

  /**
   * Fabric method.
   */
  public static ErrorResponse create(String message, HttpStatus httpStatus,
      HttpServletRequest request) {
    return new ErrorResponse(
        ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        message,
        request.getRequestURI()
    );
  }
}
