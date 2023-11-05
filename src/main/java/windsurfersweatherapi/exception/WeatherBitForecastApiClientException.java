package windsurfersweatherapi.exception;

/**
 * Exception in case of WeatherBitForecastApi 4xx response.
 */
public class WeatherBitForecastApiClientException extends RuntimeException {

  public WeatherBitForecastApiClientException(int statusCode) {
    super(String.format("WeatherBitForecastApi respond with client error. Status code: '%d'",
        statusCode));
  }
}
