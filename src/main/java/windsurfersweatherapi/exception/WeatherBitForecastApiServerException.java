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
    super(String.format("WeatherBitForecastApi responses with server reasonPhrase. "
        + "External service statusCode code: '%d'. Try to repeat request later", statusCode));
    this.statusCode = statusCode;
  }
}
