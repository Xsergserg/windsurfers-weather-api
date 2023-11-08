package windsurfersweatherapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WindsurfersWeatherApiApplicationIT {

  @Test
  @DisplayName("Main call should not throw any exceptions")
  public void shouldNotThrowAny() {
    WindsurfersWeatherApiApplication.main(new String[]{});
  }
}