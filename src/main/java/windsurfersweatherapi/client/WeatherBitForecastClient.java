package windsurfersweatherapi.client;

import java.util.Date;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import windsurfersweatherapi.enums.Location;
import windsurfersweatherapi.model.WeatherForecast;

/**
 * Client receives date and location as an argument and fetches weather forecast for the next 16
 * days from received date from <a href="https://www.weatherbit.io/">https://www.weatherbit.io/</a>.
 */
@NoArgsConstructor
public class WeatherBitForecastClient {

  @Value("${app.weatherbit.api_key}")
  private String apiKey;
  static final int DAYS = 16;
  static final String UNITS = "M";
  static final String LANG = "en";

  public List<WeatherForecast> fetchForecasts(Date date, Location location) {
    return null;
  }
}
