package windsurfersweatherapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import windsurfersweatherapi.client.WebClientFabric;

/**
 * Client receives date and location as an argument and fetches weather forecast for the next 16
 * days from received date from <a
 * href="https://www.weatherbit.io/">https://www.weatherbit.io/</a>.
 */
@Configuration
public class WeatherBitForecastWebClientConfig {

  @Value("${app.weatherbit.url}")
  private String weatherBitForecastUrl;
  @Value("${app.weatherbit.connection_timeout_in_seconds}")
  private int connectionTimeoutInSeconds;
  @Value("${app.weatherbit.response_timeout_in_seconds}")
  private int responseTimeoutInSeconds;

  @Bean
  WebClient webClient() {
    return WebClientFabric.create(
        responseTimeoutInSeconds,
        connectionTimeoutInSeconds,
        weatherBitForecastUrl
    );
  }
}