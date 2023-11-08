package windsurfersweatherapi.controller.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import windsurfersweatherapi.exception.ValidationException;

/** Validator for WeatherForecast controller. */
@Component
public class WeatherForecastControllerValidator {

  private final Pattern datePattern = Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d$");
  private static final String DATE_FORMAT_DESCRIPTION = "Date should be in format 'yyyy-mm-dd', "
      + "where yyyy - year(4 digits), mm - month(01-12), dd - day(01-31)";

  /**
   * Method validates date request parameter and parse LocalDate.
   *
   * @param date Optional< String > contains date string
   * @return LocalDate or throw ValidationException if validation is failed
   */
  public LocalDate validateAndParseDate(Optional<String> date) {
    if (date.isEmpty()) {
      throw new ValidationException("Request parameter 'date' is required");
    }
    String localDate = date.get();
    if (!datePattern.matcher(localDate).find()) {
      throw new ValidationException(
          String.format("Request parameter 'date'='%s' has wrong format. %s",
              localDate, DATE_FORMAT_DESCRIPTION));
    }
    try {
      return LocalDate.parse(localDate);
    } catch (DateTimeException e) {
      throw new ValidationException(
          String.format(
              "Request parameter 'date'='%s' is not a valid date. %s",
              localDate, DATE_FORMAT_DESCRIPTION));
    }
  }
}