package windsurfersweatherapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper and object configuration.
 */
@Configuration
public class MapperConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
