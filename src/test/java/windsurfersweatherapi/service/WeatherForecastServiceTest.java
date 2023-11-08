package windsurfersweatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import windsurfersweatherapi.client.WeatherBitForecastApiClient;
import windsurfersweatherapi.mapper.WeatherBitForecastResponseMapper;
import windsurfersweatherapi.model.WeatherForecast;
import windsurfersweatherapi.service.validator.WeatherForecastServiceValidator;

@ExtendWith(MockitoExtension.class)
class WeatherForecastServiceTest {

  @Mock
  private WeatherBitForecastResponseMapper mapper;
  @Mock
  private WeatherBitForecastApiClient weatherBitForecastApiClient;

  private WeatherForecastService service;

  @BeforeEach
  void setUp(){
    service = new WeatherForecastService(
        mapper,
        weatherBitForecastApiClient,
        new WeatherForecastServiceValidator(
        Clock.systemUTC())
    );
  }

  @Test
  @DisplayName("Service returns correct value")
  void ServiceReturnsCorrectValue() {
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
  void ServiceReturnsNull() {
    var actualDate = LocalDate.of(2023, 11, 11);
    when(weatherBitForecastApiClient.fetchForecasts(any())).thenReturn(null);
    when(mapper.convertToWeatherForecasts(null)).thenReturn(
        List.of(
            createWeatherForecast(actualDate, "TooWindyCity", 18.0, 36.0),
            createWeatherForecast(actualDate.plusDays(1), "TooLateCity", 30.0, 15.0)
        ));

    assertThat(service.getBestWindsurfersForecast(actualDate)).isNull();
  }

  private WeatherForecast createWeatherForecast(LocalDate date, String city, Double avgTemp,
      Double avgWindSpeed) {
    return WeatherForecast.create(date, city, null, null, null, avgTemp, avgWindSpeed);
  }
}