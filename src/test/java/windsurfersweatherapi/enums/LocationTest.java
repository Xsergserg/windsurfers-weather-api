package windsurfersweatherapi.enums;

import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import windsurfersweatherapi.model.Coordinates;

class LocationTest {

  @Test
  @DisplayName("Properties should be appropriate")
  void locationPropertiesTest() {
    assertThat(Location.PISSOURI)
        .returns("Pissouri", from(Location::getCity))
        .returns("Cyprus", from(Location::getCountry));
    assertThat(Location.JASTARNIA)
        .returns(new Coordinates(18.67873, 54.69606), from(Location::getCoordinates));
  }
}