package windsurfersweatherapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import windsurfersweatherapi.client.WeatherBitForecastClient;

/**
 * Spring beans(singletons) configuration.
 */
@Configuration
public class SingletonBeanConfig {
  @Bean
  public WeatherBitForecastClient weatherBitForecastClient() {
    return new WeatherBitForecastClient();
  }
}
