package windsurfersweatherapi.controller;

import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static windsurfersweatherapi.factory.WeatherBitForecastResponseFactory.bridgetownWeatherBitForecastFullResponse;
import static windsurfersweatherapi.factory.WeatherBitForecastResponseFactory.pissouriWeatherBitForecastFullResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import windsurfersweatherapi.client.WeatherBitForecastApiClient;
import windsurfersweatherapi.client.WebClientFabric;
import windsurfersweatherapi.controller.validator.WeatherForecastControllerValidator;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.exception.CustomRetryExhaustedException;
import windsurfersweatherapi.exception.ValidationException;
import windsurfersweatherapi.exception.WeatherForecastControllerExceptionHandler;
import windsurfersweatherapi.factory.WeatherBitForecastResponseFactory;
import windsurfersweatherapi.mapper.WeatherForecastDtoMapper;
import windsurfersweatherapi.service.WeatherForecastService;

public class WeatherForecastControllerTest {

  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  class Integration {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    @DisplayName("Real integration test return status 200")
    void shouldReturn200() {
      assertThat(this.restTemplate.getForEntity("http://localhost:" + port + contextPath +
          "/weatherForecast/bestForWindsurfing?date=" + LocalDate.now().plusDays(5), String.class))
          .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));
    }
  }

  @Nested
  class StatusCode {

    private MockMvc mockMvc;
    private WeatherForecastControllerValidator validator =
        mock(WeatherForecastControllerValidator.class);

    @BeforeEach
    public void setup() {
      var service = mock(WeatherForecastService.class);
      var mapper = mock(WeatherForecastDtoMapper.class);
      this.mockMvc = MockMvcBuilders
          .standaloneSetup(new WeatherForecastController(service, validator, mapper))
          .setControllerAdvice(new WeatherForecastControllerExceptionHandler())
          .build();
      when(service.getBestWindsurfersForecast(any())).thenReturn(null);
      when(mapper.convertToWeatherForecastsDto(any())).thenReturn(null);
    }

    @Test
    @DisplayName("In case of valid request controller should return OK(200) statusCode")
    void shouldReturn200Status() throws Exception {
      when(validator.validateAndParseDate(any())).thenReturn(null);
      mockMvc.perform(get("/weatherForecast/bestForWindsurfing?date=2023-11-11"))
          .andExpect(status().isOk());
    }

    @Test
    @DisplayName("In case of not valid request controller should return BAD_REQUEST(400) statusCode")
    void shouldReturn400Status() throws Exception {
      when(validator.validateAndParseDate(any())).thenThrow(
          new ValidationException("test_message"));
      mockMvc.perform(get("/weatherForecast/bestForWindsurfing?date=2023-11-11"))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("In case of CustomRetryExhaustedException controller should return INTERNAL_SERVER_ERROR(500) statusCode")
    void shouldReturn500Status() throws Exception {
      when(validator.validateAndParseDate(any())).thenThrow(
          new CustomRetryExhaustedException(500, new RuntimeException()));
      mockMvc.perform(get("/weatherForecast/bestForWindsurfing?date=2023-11-11"))
          .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("In case of uncaught exception controller should return INTERNAL_SERVER_ERROR(500) statusCode")
    void uncaughtExceptionShouldReturn500Status() throws Exception {
      when(validator.validateAndParseDate(any())).thenThrow(new RuntimeException());
      mockMvc.perform(get("/weatherForecast/bestForWindsurfing?date=2023-11-11"))
          .andExpect(status().isInternalServerError());
    }
  }
}