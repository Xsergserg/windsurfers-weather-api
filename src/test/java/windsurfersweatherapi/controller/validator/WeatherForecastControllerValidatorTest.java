package windsurfersweatherapi.controller.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import windsurfersweatherapi.exception.ValidationException;

class WeatherForecastControllerValidatorTest {

  private final WeatherForecastControllerValidator validator = new WeatherForecastControllerValidator();

  @Test
  @DisplayName("Validator should parse and return correct LocalDate")
  void shouldReturnLocalDate() {
    assertThat(validator.validateAndParseDate(Optional.of("2023-11-12"))).isEqualTo(
        LocalDate.of(2023, 11, 12));
  }

  @Test
  @DisplayName("Validator should throw exception in case of empty optional argument")
  void shouldThrowExceptionWithEmptyOptionAsArgument() {
    assertThatExceptionOfType(ValidationException.class).isThrownBy(
        () -> validator.validateAndParseDate(Optional.empty()));
  }

  @Test
  @DisplayName("Validator should throw exception in case of wrong format date argument")
  void shouldThrowExceptionWithBadDateStringAsArgument() {
    assertThatExceptionOfType(ValidationException.class).isThrownBy(
        () -> validator.validateAndParseDate(Optional.of("test")));
  }

  @Test
  @DisplayName("Validator should throw exception in case of not valid date argument")
  void shouldThrowExceptionWithNotValidDateStringAsArgument() {
    assertThatExceptionOfType(ValidationException.class).isThrownBy(
        () -> validator.validateAndParseDate(Optional.of("2023-50-50")));
  }
}