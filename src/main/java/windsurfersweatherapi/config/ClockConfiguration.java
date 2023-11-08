package windsurfersweatherapi.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configure clock for UTC zone. */
@Configuration
public class ClockConfiguration {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }
}
