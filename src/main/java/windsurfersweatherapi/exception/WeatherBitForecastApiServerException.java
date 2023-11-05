package windsurfersweatherapi.exception;

import lombok.Getter;

/**
 * Exception in case of WeatherBitForecastApi 5xx response.
 */
@Getter
public class WeatherBitForecastApiServerException extends RuntimeException {

  public final int statusCode;

  /**
   * Base constructor.
   */
  public WeatherBitForecastApiServerException(int statusCode) {
    super(String.format("WeatherBitForecastApi responses with server error. Status code: '%d'",
        statusCode));
    this.statusCode = statusCode;
  }
}
