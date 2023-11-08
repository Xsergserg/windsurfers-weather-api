package windsurfersweatherapi.model;

import java.time.LocalDate;

/**
 * Weather forecast model.
 */
public record WeatherForecast(
    LocalDate date,
    LocationData locationData,
    WeatherData weatherData
) {

  /**
   * Fabric method.
   *
   * @param date         forecast date
   * @param city         location
   * @param countryCode  country code
   * @param longitude    location longitude
   * @param latitude     location latitude
   * @param avgTemp      average temperature
   * @param avgWindSpeed average wind speed
   * @return WeatherForecast
   */
  public static WeatherForecast create(
      LocalDate date, String city, String countryCode, Double longitude, Double latitude,
      Double avgTemp, Double avgWindSpeed) {
    return new WeatherForecast(
        date,
        new LocationData(city, countryCode, new Coordinates(longitude, latitude)),
        new WeatherData(avgTemp, avgWindSpeed)
    );
  }
}
