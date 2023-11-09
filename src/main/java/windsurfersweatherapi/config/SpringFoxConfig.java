package windsurfersweatherapi.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger configuration.
 */
@Configuration
public class SpringFoxConfig {

  /**
   * Swagger API enabling.
   *
   * @return Docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("windsurfersweatherapi.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  /**
   * Endpoint handling mapping.
   *
   * @param webEndpointsSupplier  webEndpointsSupplier
   * @param endpointMediaTypes    endpointMediaTypes
   * @param corsProperties        corsProperties
   * @param webEndpointProperties webEndpointProperties
   * @return WebMvcEndpointHandlerMapping
   */
  @Bean
  public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
      WebEndpointsSupplier webEndpointsSupplier,
      EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
      WebEndpointProperties webEndpointProperties) {
    Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
    List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>(webEndpoints);
    String basePath = webEndpointProperties.getBasePath();
    EndpointMapping endpointMapping = new EndpointMapping(basePath);
    boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties,
        basePath);
    return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
        corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
        shouldRegisterLinksMapping, null);
  }


  private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties,
      String basePath) {
    return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath));
  }
}
