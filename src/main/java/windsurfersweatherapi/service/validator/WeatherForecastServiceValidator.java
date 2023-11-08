package windsurfersweatherapi.service.validator;

import java.time.Clock;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import windsurfersweatherapi.exception.ResponseValidationException;
import windsurfersweatherapi.exception.ValidationException;
import windsurfersweatherapi.model.LocationData;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Validator for weather forecast service.
 */
@Component
public class WeatherForecastServiceValidator {

  private final Clock clock;

  @Autowired
  public WeatherForecastServiceValidator(Clock clock) {
    this.clock = clock;
  }

  /**
   * Validate date in range.
   *
   * @param actualDate chosen date
   */
  public void validateDate(LocalDate actualDate) {
    LocalDate fromDate = LocalDate.now(clock);
    LocalDate toDate = fromDate.plusDays(15);
    if (actualDate.isBefore(fromDate) || actualDate.isAfter(toDate)) {
      throw new ValidationException(
          String.format("Date '%s' is out of range. Date should be in range [%s, %s]",
              actualDate, fromDate, toDate));
    }
  }

  /**
   * Validation weather response.
   *
   * @param forecast WeatherForecast
   */
  public void validateForecast(WeatherForecast forecast) {
    if (!weatherDataIsValid(forecast.weatherData())
        || !locationDataIsValid(forecast.locationData())) {
      throw new ResponseValidationException(String.format(
          "Weather forecast is not valid. "
              + "Forecast should contain location data, average temperature "
              + "and average wind speed. Forecast: '%s'", forecast));
    }
  }

  /**
   * Validate weather response data.
   *
   * @param weatherData WeatherData
   * @return true if data is valid
   */
  private boolean weatherDataIsValid(WeatherData weatherData) {
    return weatherData.averageTemperature() != null && weatherData.averageWindSpeed() != null
        && weatherData.averageWindSpeed() >= 0;
  }

  private boolean locationDataIsValid(LocationData locationData) {
    return locationData.city() != null
        || (locationData.coordinates().longitude() != null
        && locationData.coordinates().latitude() != null);
  }
}
