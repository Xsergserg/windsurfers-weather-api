package windsurfersweatherapi.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ErrorResponseTest {

  private final HttpServletRequest request = mock(HttpServletRequest.class);

  @BeforeEach
  void setUp() {
    when(request.getRequestURI()).thenReturn("/test");
  }

  @Test
  @DisplayName("Should return correct response")
  void shouldReturnCorrectResponse() {
    var actual = ErrorResponse.create("test_message", HttpStatus.BAD_REQUEST, request);

    assertThat(actual)
        .returns("test_message", from(ErrorResponse::message))
        .returns(400, from(ErrorResponse::statusCode))
        .returns("Bad Request", from(ErrorResponse::reasonPhrase))
        .returns("/test", from(ErrorResponse::path));
    assertThat(actual.time()).isNotNull();
  }

  @Test
  @DisplayName("Should return correct response without message")
  void shouldReturnCorrectResponseWithoutMessage() {
    var actual = ErrorResponse.create(HttpStatus.BAD_REQUEST, request);

    assertThat(actual)
        .returns(null, from(ErrorResponse::message))
        .returns(400, from(ErrorResponse::statusCode))
        .returns("Bad Request", from(ErrorResponse::reasonPhrase))
        .returns("/test", from(ErrorResponse::path));
    assertThat(actual.time()).isNotNull();
  }
}