package windsurfersweatherapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper and object configuration.
 */
@Configuration
public class MapperConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
