package windsurfersweatherapi.service.validator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doReturn;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import windsurfersweatherapi.exception.ResponseValidationException;
import windsurfersweatherapi.exception.ValidationException;
import windsurfersweatherapi.model.Coordinates;
import windsurfersweatherapi.model.LocationData;
import windsurfersweatherapi.model.WeatherData;
import windsurfersweatherapi.model.WeatherForecast;

@ExtendWith(MockitoExtension.class)
class WeatherForecastServiceValidatorTest {

  @Mock
  private static Clock clock;
  @InjectMocks
  private WeatherForecastServiceValidator validator;

  @Nested
  class DateValidation {

    private Clock fixedClock;

    @Test
    @DisplayName("Date is in the range should be validated")
    void shouldNotThrow() {
      var actualDate = LocalDate.of(2023, 11, 10);
      var actualDateFirst = LocalDate.of(2023, 11, 5);
      var actualDateLast = LocalDate.of(2023, 11, 20);
      var fromDate = LocalDate.of(2023, 11, 5);
      fixedClock = Clock.fixed(fromDate.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
      doReturn(fixedClock.instant()).when(clock).instant();
      doReturn(fixedClock.getZone()).when(clock).getZone();

      assertThatNoException().isThrownBy(() -> validator.validateDate(actualDate));
      assertThatNoException().isThrownBy(() -> validator.validateDate(actualDateFirst));
      assertThatNoException().isThrownBy(() -> validator.validateDate(actualDateLast));
    }

    @Test
    @DisplayName("Date is out of the range should not be validated")
    void shouldThrowValidationExceptionIfOutOfRange() {
      var actualDateBefore = LocalDate.of(2023, 11, 4);
      var actualDateAfter = LocalDate.of(2023, 11, 21);
      var fromDate = LocalDate.of(2023, 11, 5);
      fixedClock = Clock.fixed(fromDate.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
      doReturn(fixedClock.instant()).when(clock).instant();
      doReturn(fixedClock.getZone()).when(clock).getZone();

      assertThatExceptionOfType(ValidationException.class).isThrownBy(
          () -> validator.validateDate(actualDateBefore));
      assertThatExceptionOfType(ValidationException.class).isThrownBy(
          () -> validator.validateDate(actualDateAfter));
    }

    @Test
    @DisplayName("Date is out of the range should not be validated even if it's in local time range")
    void shouldThrowValidationExceptionIfOutOfRangeUTC() {
      var actualDateAfter = LocalDate.of(2023, 11, 21);
      var fromDate = LocalDate.of(2023, 11, 5);
      fixedClock = Clock.fixed(fromDate.atStartOfDay().toInstant(ZoneOffset.of("+2")),
          ZoneOffset.UTC);
      doReturn(fixedClock.instant()).when(clock).instant();
      doReturn(fixedClock.getZone()).when(clock).getZone();

      assertThatExceptionOfType(ValidationException.class).isThrownBy(
          () -> validator.validateDate(actualDateAfter));
    }

    @Test
    @DisplayName("Date is in the range should be validated even if it's not in local time range")
    void shouldThrowValidationExceptionIfInRangeUTC() {
      var actualDate = LocalDate.of(2023, 11, 4);
      var fromDate = LocalDate.of(2023, 11, 5);
      fixedClock = Clock.fixed(fromDate.atStartOfDay().toInstant(ZoneOffset.of("+2")),
          ZoneOffset.UTC);
      doReturn(fixedClock.instant()).when(clock).instant();
      doReturn(fixedClock.getZone()).when(clock).getZone();

      assertThatNoException().isThrownBy(() -> validator.validateDate(actualDate));
    }
  }

  @Nested
  class WeatherValidation {

    @Test
    @DisplayName("Correct weather forecast should be validated")
    void shouldBeValidated() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData("City17", "BG", new Coordinates(1.0, 1.0)), new WeatherData(1.0, 1.0));

      assertThatNoException().isThrownBy(() -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without city and coordinates should not be validated")
    void shouldNotBeValidatedWithNullCityAndCoordinates() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData(null, "BG", new Coordinates(null, null)), new WeatherData(1.0, 1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without city and latitude should not be validated")
    void shouldNotBeValidatedWithNullCityAndLatitude() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData(null, "BG", new Coordinates(1.0, null)), new WeatherData(1.0, 1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without city and latitude should not be validated")
    void shouldNotBeValidatedWithNullCityAndLongitude() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData(null, "BG", new Coordinates(null, 1.0)), new WeatherData(1.0, 1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without average wind sped should not be validated")
    void shouldNotBeValidatedWithNegativeAverageWindSpeed() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData("City17", "BG", new Coordinates(1.0, 1.0)), new WeatherData(1.0, -1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without average temperature should not be validated")
    void shouldNotBeValidatedWithNullAverageTemp() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData("City17", "BG", new Coordinates(1.0, 1.0)), new WeatherData(null, 1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast with negative average temperature should not be validated")
    void shouldNotBeValidatedWithNegativeAverageTemp() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData("City17", "BG", new Coordinates(1.0, 1.0)), new WeatherData(-1.0, -1.0));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }

    @Test
    @DisplayName("Weather forecast without average wind sped should not be validated")
    void shouldNotBeValidatedWithNullAverageWindSpeed() {
      var actual = new WeatherForecast(LocalDate.of(2023, 11, 4),
          new LocationData("City17", "BG", new Coordinates(1.0, 1.0)), new WeatherData(1.0, null));

      assertThatExceptionOfType(ResponseValidationException.class).isThrownBy(
          () -> validator.validateForecast(actual));
    }
  }
}