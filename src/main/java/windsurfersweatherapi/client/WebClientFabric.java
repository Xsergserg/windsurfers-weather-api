package windsurfersweatherapi.client;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * Fabric for creating WebClients.
 */
public class WebClientFabric {

  /**
   * Method creates Web client with specific parameters.
   *
   * @param responseTimeoutInSeconds   specifies the maximum amount of wait time for an expected
   *                                   response
   * @param connectionTimeoutInSeconds specifies the maximum amount of wait time for connection
   *                                   establishment
   * @param baseUrl                    specifies basic url for client
   * @return Webclient
   */
  public static WebClient create(int responseTimeoutInSeconds, int connectionTimeoutInSeconds,
      String baseUrl) {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(responseTimeoutInSeconds))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutInSeconds * 1000);
    return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(baseUrl).build();
  }
}
