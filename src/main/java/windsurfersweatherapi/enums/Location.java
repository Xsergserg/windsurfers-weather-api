package windsurfersweatherapi.enums;

import lombok.Getter;
import lombok.ToString;
import windsurfersweatherapi.model.Coordinates;

/**
 * Locations enum. Enum define which locations are using for weather conditions comparing. Can be
 * extended: with city name (f. e. JASTARNIA("Jastarnia")) or with city and country name (f. e.
 * JASTARNIA("Jastarnia", "Poland")) or with city coordinates long/lat (f. e. JASTARNIA(18.67873,
 * 54.69606) or
 */
@ToString
@Getter
public enum Location {
  JASTARNIA(18.67873, 54.69606),
  BRIDGETOWN("Bridgetown"),
  FORTALEZA("Fortaleza"),
  PISSOURI("Pissouri", "Cyprus"),
  LEMORNE("LeMorne");

  private final String city;
  private final String country;
  private final Coordinates coordinates;

  Location(String city) {
    this.city = city;
    this.country = null;
    this.coordinates = null;
  }

  Location(String city, String country) {
    this.city = city;
    this.country = country;
    this.coordinates = null;
  }

  Location(Double longitude, Double latitude) {
    this.country = null;
    this.city = null;
    this.coordinates = new Coordinates(longitude, latitude);
  }
}
