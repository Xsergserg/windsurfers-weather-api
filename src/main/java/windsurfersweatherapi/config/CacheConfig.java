package windsurfersweatherapi.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Cache configuration.
 */
@Configuration
public class CacheConfig {

  @Value("${app.weatherbit.cache_ttl_seconds}")
  private int cacheTtl;

  /**
   * Cache bean constructor.
   *
   * @return Cache
   */
  @Bean
  public Cache<LocalDate, List<WeatherForecast>> cache() {
    return Caffeine
        .newBuilder()
        .expireAfterWrite(cacheTtl, TimeUnit.SECONDS)
        .build();
  }
}
