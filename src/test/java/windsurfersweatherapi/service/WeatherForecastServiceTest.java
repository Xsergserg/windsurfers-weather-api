package windsurfersweatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static windsurfersweatherapi.factory.WeatherBitForecastResponseFactory.pissouriWeatherBitForecastFullResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import windsurfersweatherapi.client.WeatherBitForecastApiClient;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.mapper.WeatherBitForecastResponseMapper;
import windsurfersweatherapi.model.WeatherForecast;
import windsurfersweatherapi.service.validator.WeatherForecastServiceValidator;

@ExtendWith(MockitoExtension.class)
class WeatherForecastServiceTest {

  @Spy
  private final WeatherBitForecastResponseMapper mapper = new WeatherBitForecastResponseMapper();

  @Mock
  private WeatherBitForecastApiClient weatherBitForecastApiClient;

  private WeatherForecastService service;

  @BeforeEach
  void setUp() {
    service = new WeatherForecastService(
        mapper,
        weatherBitForecastApiClient,
        new WeatherForecastServiceValidator(
            Clock.fixed(
                LocalDate.of(2023, 11, 5).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC)),
        Caffeine
            .newBuilder()
            .expireAfterWrite(100, TimeUnit.SECONDS)
            .build()
    );
  }

  @Test
  @DisplayName("Service returns correct value")
  void shouldReturnCorrectValue() {
    var actualDate = LocalDate.of(2023, 11, 11);
    var expected = createWeatherForecast(actualDate, "City17", 10.0, 10.0);
    when(weatherBitForecastApiClient.fetchForecasts(any())).thenReturn(null);
    when(mapper.convertToWeatherForecasts(null)).thenReturn(
        List.of(
            expected,
            createWeatherForecast(actualDate, "Gotham", 18.0, 7.0),
            createWeatherForecast(actualDate, "TooWindyCity", 18.0, 36.0),
            createWeatherForecast(actualDate.plusDays(1), "TooLateCity", 30.0, 15.0)
        ));

    assertThat(service.getBestWindsurfersForecast(actualDate)).isEqualTo(expected);
  }

  @Test
  @DisplayName("Service returns null if no suit weather forecast")
  void shouldReturnNull() {
    var actualDate = LocalDate.of(2023, 11, 11);
    when(weatherBitForecastApiClient.fetchForecasts(any())).thenReturn(null);
    when(mapper.convertToWeatherForecasts(null)).thenReturn(
        List.of(
            createWeatherForecast(actualDate, "TooWindyCity", 18.0, 36.0),
            createWeatherForecast(actualDate.plusDays(1), "TooLateCity", 30.0, 15.0)
        ));

    assertThat(service.getBestWindsurfersForecast(actualDate)).isNull();
  }

  @Test
  @DisplayName("Cache is working as expected")
  void shouldReturnCorrectCachedValue() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    var actualDate = LocalDate.of(2023, 11, 15);
    var expected = WeatherForecast.create(
        actualDate,
        "Pissouri",
        "CY",
        32.70132,
        34.66942,
        15.3,
        9.4
    );
    when(weatherBitForecastApiClient.fetchForecasts(any())).thenReturn(objectMapper.readTree(
        pissouriWeatherBitForecastFullResponse));
    service.getBestWindsurfersForecast(actualDate);

    assertThat(service.getBestWindsurfersForecast(actualDate)).isEqualTo(expected);
    verify(weatherBitForecastApiClient, times(Location.values().length)).fetchForecasts(any());
  }

  private WeatherForecast createWeatherForecast(LocalDate date, String city, Double avgTemp,
      Double avgWindSpeed) {
    return WeatherForecast.create(date, city, null, null, null, avgTemp, avgWindSpeed);
  }
}