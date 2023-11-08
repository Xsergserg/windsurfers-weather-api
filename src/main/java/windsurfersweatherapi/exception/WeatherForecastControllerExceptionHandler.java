package windsurfersweatherapi.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import windsurfersweatherapi.controller.WeatherForecastController;

/**
 * Exception handler for WeatherForecastController.
 */
@Slf4j
@ControllerAdvice(assignableTypes = {WeatherForecastController.class})
public class WeatherForecastControllerExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  private ErrorResponse handleValidationException(RuntimeException e, HttpServletRequest request) {
    log.error("Validation exception", e);
    return ErrorResponse.create(e.getMessage(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({CustomRetryExhaustedException.class,
      WeatherBitForecastApiServerException.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse handleCustomRetryExhaustedException(RuntimeException e,
      HttpServletRequest request) {
    log.error("WeatherBitForecastApi exception", e);
    return ErrorResponse.create(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse handleGlobalException(Exception e, HttpServletRequest request) {
    log.error("Internal server exception", e);
    return ErrorResponse.create("Unknown exception", HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
