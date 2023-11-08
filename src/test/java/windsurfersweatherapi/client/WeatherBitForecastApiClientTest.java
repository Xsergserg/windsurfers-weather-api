package windsurfersweatherapi.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.from;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.exception.CustomRetryExhaustedException;
import windsurfersweatherapi.exception.WeatherBitForecastApiClientException;
import windsurfersweatherapi.factory.WeatherBitForecastResponseFactory;
import windsurfersweatherapi.model.Coordinates;

class WeatherBitForecastApiClientTest {

  private static WeatherBitForecastApiClient weatherBitForecastApiClient;
  private static MockWebServer server;
  private static final int MAX_ATTEMPTS = 3;
  private final Location mockLocation = mock(Location.class);

  @BeforeEach
  void beforeEach() throws IOException {
    server = new MockWebServer();
    server.start();
    var webClient = WebClientFabric.create(1, 1,
        String.format("http://localhost:%d", server.getPort()));
    weatherBitForecastApiClient = new WeatherBitForecastApiClient("test_key", MAX_ATTEMPTS, 0,
        webClient);
  }

  @AfterAll
  static void afterAll() throws IOException {
    server.shutdown();
  }

  @Nested
  class CommonScenarios {

    @Test
    @DisplayName("Client creates correct request to weatherBitForecastApi")
    void clientFetchesForecastsRequestAsExpected() throws InterruptedException {
      server.enqueue(createRequest());
      setUpMockLocation("Pissouri", "Cyprus", null, null);
      var actualJsonNode = weatherBitForecastApiClient.fetchForecasts(mockLocation);
      var actualRequest = server.takeRequest();

      assertThat(actualRequest).returns("GET", from(RecordedRequest::getMethod))
          .returns("/?key=test_key&lang=en&units=M&days=16&city=Pissouri&country=Cyprus",
              from(RecordedRequest::getPath));
      assertThat((actualJsonNode.get("data"))).hasSize(16);
    }

    @Test
    @DisplayName("Client returns null in case of null respond from WeatherBitForecastApiClient")
    void clientReturnsEmptyList() {
      server.enqueue(new MockResponse().addHeader("Content-Type", "application/json"));
      setUpMockLocation(null, null, null, null);

      assertThat(weatherBitForecastApiClient.fetchForecasts(mockLocation)).isNull();
    }
  }

  @Nested
  class DifferentParams {

    @Test
    @DisplayName("Client creates correct request to weatherBitForecastApi with city and country")
    void clientFetchesForecastsWithCityAndCountryCorrectly() throws InterruptedException {
      server.enqueue(createRequest());
      setUpMockLocation("Pissouri", "Cyprus", null, null);
      weatherBitForecastApiClient.fetchForecasts(mockLocation);
      var actualRequest = server.takeRequest();

      assertThat(actualRequest).returns(
          "/?key=test_key&lang=en&units=M&days=16&city=Pissouri&country=Cyprus",
          from(RecordedRequest::getPath));
    }

    @Test
    @DisplayName("Client creates correct request to weatherBitForecastApi with city")
    void clientFetchesForecastsWithCityCorrectly() throws InterruptedException {
      server.enqueue(createRequest());
      setUpMockLocation("Gotham", null, null, null);
      weatherBitForecastApiClient.fetchForecasts(mockLocation);
      var actualRequest = server.takeRequest();

      assertThat(actualRequest).returns("/?key=test_key&lang=en&units=M&days=16&city=Gotham",
          from(RecordedRequest::getPath));
    }

    @Test
    @DisplayName("Client creates correct request to weatherBitForecastApi with coordinates")
    void clientFetchesForecastsWithCoordinatesCorrectly() throws InterruptedException {
      server.enqueue(createRequest());
      setUpMockLocation(null, null, 1.0, 2.0);
      weatherBitForecastApiClient.fetchForecasts(mockLocation);
      var actualRequest = server.takeRequest();

      assertThat(actualRequest).returns("/?key=test_key&lang=en&units=M&days=16&lat=2.0&lon=1.0",
          from(RecordedRequest::getPath));
    }

    @Test
    @DisplayName("Client creates correct request to weatherBitForecastApi with coordinates and city")
    void clientFetchesForecastsWithCoordinatesAndCityCorrectly() throws InterruptedException {
      server.enqueue(createRequest());
      setUpMockLocation("City17", null, 1.0, 2.0);
      weatherBitForecastApiClient.fetchForecasts(mockLocation);
      var actualRequest = server.takeRequest();

      assertThat(actualRequest).returns("/?key=test_key&lang=en&units=M&days=16&lat=2.0&lon=1.0",
          from(RecordedRequest::getPath));
    }
  }

  @Nested
  class ExceptionScenarios {

    @Test
    @DisplayName("Client should throws WeatherBitForecastApiClientException after first try")
    void clientShouldThrowClientException() {
      server.enqueue(new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()));
      setUpMockLocation("Pissouri", "Cyprus", null, null);

      assertThatExceptionOfType(WeatherBitForecastApiClientException.class).isThrownBy(
          () -> weatherBitForecastApiClient.fetchForecasts(mockLocation));
      assertThat(server.getRequestCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Client should throws CustomRetryExhaustedException after max retry attempts")
    void clientShouldThrowRetryExhaustedException() {
      server.enqueue(new MockResponse().setResponseCode(HttpStatus.SERVICE_UNAVAILABLE.value()));
      server.enqueue(new MockResponse().setResponseCode(HttpStatus.SERVICE_UNAVAILABLE.value()));
      server.enqueue(new MockResponse().setResponseCode(HttpStatus.SERVICE_UNAVAILABLE.value()));
      server.enqueue(new MockResponse().setResponseCode(HttpStatus.SERVICE_UNAVAILABLE.value()));
      setUpMockLocation("Pissouri", "Cyprus", null, null);

      assertThatExceptionOfType(CustomRetryExhaustedException.class).isThrownBy(
          () -> weatherBitForecastApiClient.fetchForecasts(mockLocation));
      assertThat(server.getRequestCount()).isEqualTo(4);
    }
  }

  private void setUpMockLocation(String city, String country,
      Double longitude, Double latitude) {
    when(mockLocation.getCity()).thenReturn(city);
    when(mockLocation.getCountry()).thenReturn(country);
    if (longitude == null && latitude == null) {
      when(mockLocation.getCoordinates()).thenReturn(null);
    } else {
      when(mockLocation.getCoordinates()).thenReturn(new Coordinates(longitude, latitude));
    }
  }

  private MockResponse createRequest() {
    return new MockResponse().setBody(
            WeatherBitForecastResponseFactory.pissouriWeatherBitForecastFullResponse())
        .addHeader("Content-Type", "application/json");
  }
}