package windsurfersweatherapi.enums;

import windsurfersweatherapi.model.Coordinates;

/**
 * Locations enum.
 * Enum define which locations are using for weather conditions comparing.
 * Can be extended:
 * with city name (f. e. JASTARNIA("Jastarnia")) or
 * with city and country name (f. e. JASTARNIA("Jastarnia", "Poland")) or
 * with city coordinates long/lat (f. e. JASTARNIA(-78.543, 38.123) or
 */
public enum Location {
  JASTARNIA(-78.543, 38.123),
  BRIDGETOWN("Bridgetown"),
  FORTALEZA("Fortaleza"),
  PISSOURI("Pissouri", "Cyprus"),
  LEMORNE("LeMorne");

  public final String city;
  public final String country;
  public final Coordinates coordinates;

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
