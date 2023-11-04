package windsurfersweatherapi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperFactory {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static String pissouriWeatherBitForecastResponseJson() {
    return "{\n" +
        "  \"city_name\": \"Pissouri\",\n" +
        "  \"country_code\": \"CY\",\n" +
        "  \"data\": [\n" +
        "    {\n" +
        "      \"app_max_temp\": 24.6,\n" +
        "      \"app_min_temp\": 18.2,\n" +
        "      \"clouds\": 0,\n" +
        "      \"clouds_hi\": 0,\n" +
        "      \"clouds_low\": 23,\n" +
        "      \"clouds_mid\": 0,\n" +
        "      \"datetime\": \"2023-11-04\",\n" +
        "      \"dewpt\": 17.1,\n" +
        "      \"high_temp\": 24.3,\n" +
        "      \"low_temp\": 18.4,\n" +
        "      \"max_dhi\": null,\n" +
        "      \"max_temp\": 24.3,\n" +
        "      \"min_temp\": 18.2,\n" +
        "      \"moon_phase\": 0.490503,\n" +
        "      \"moon_phase_lunation\": 0.72,\n" +
        "      \"moonrise_ts\": 1699129593,\n" +
        "      \"moonset_ts\": 1699095974,\n" +
        "      \"ozone\": 275.9,\n" +
        "      \"pop\": 0,\n" +
        "      \"precip\": 0,\n" +
        "      \"pres\": 987.4,\n" +
        "      \"rh\": 81,\n" +
        "      \"slp\": 1015.1,\n" +
        "      \"snow\": 0,\n" +
        "      \"snow_depth\": 0,\n" +
        "      \"sunrise_ts\": 1699071137,\n" +
        "      \"sunset_ts\": 1699109611,\n" +
        "      \"temp\": 21.1,\n" +
        "      \"ts\": 1699048860,\n" +
        "      \"uv\": 4.5,\n" +
        "      \"valid_date\": \"2023-11-04\",\n" +
        "      \"vis\": 24.128,\n" +
        "      \"weather\": {\n" +
        "        \"description\": \"Clear Sky\",\n" +
        "        \"code\": 800,\n" +
        "        \"icon\": \"c01d\"\n" +
        "      },\n" +
        "      \"wind_cdir\": \"ESE\",\n" +
        "      \"wind_cdir_full\": \"east-southeast\",\n" +
        "      \"wind_dir\": 108,\n" +
        "      \"wind_gust_spd\": 3.8,\n" +
        "      \"wind_spd\": 1.7\n" +
        "    },\n" +
        "    {\n" +
        "      \"app_max_temp\": 25,\n" +
        "      \"app_min_temp\": 18.4,\n" +
        "      \"clouds\": 8,\n" +
        "      \"clouds_hi\": 18,\n" +
        "      \"clouds_low\": 9,\n" +
        "      \"clouds_mid\": 5,\n" +
        "      \"datetime\": \"2023-11-05\",\n" +
        "      \"dewpt\": 16.7,\n" +
        "      \"high_temp\": 24.9,\n" +
        "      \"low_temp\": 18.9,\n" +
        "      \"max_dhi\": null,\n" +
        "      \"max_temp\": 24.9,\n" +
        "      \"min_temp\": 18.4,\n" +
        "      \"moon_phase\": 0.394735,\n" +
        "      \"moon_phase_lunation\": 0.75,\n" +
        "      \"moonrise_ts\": 1699219563,\n" +
        "      \"moonset_ts\": 1699184177,\n" +
        "      \"ozone\": 280.6,\n" +
        "      \"pop\": 0,\n" +
        "      \"precip\": 0,\n" +
        "      \"pres\": 988,\n" +
        "      \"rh\": 79,\n" +
        "      \"slp\": 1015.8,\n" +
        "      \"snow\": 0,\n" +
        "      \"snow_depth\": 0,\n" +
        "      \"sunrise_ts\": 1699157593,\n" +
        "      \"sunset_ts\": 1699195959,\n" +
        "      \"temp\": 21.1,\n" +
        "      \"ts\": 1699135260,\n" +
        "      \"uv\": 4.4,\n" +
        "      \"valid_date\": \"2023-11-05\",\n" +
        "      \"vis\": 24.128,\n" +
        "      \"weather\": {\n" +
        "        \"description\": \"Few clouds\",\n" +
        "        \"code\": 801,\n" +
        "        \"icon\": \"c02d\"\n" +
        "      },\n" +
        "      \"wind_cdir\": \"ESE\",\n" +
        "      \"wind_cdir_full\": \"east-southeast\",\n" +
        "      \"wind_dir\": 108,\n" +
        "      \"wind_gust_spd\": 3.8,\n" +
        "      \"wind_spd\": 1.7\n" +
        "    }\n" +
        "  ],\n" +
        "  \"lat\": \"34.66942\",\n" +
        "  \"lon\": \"32.70132\",\n" +
        "  \"state_code\": \"05\",\n" +
        "  \"timezone\": \"Asia/Nicosia\"\n" +
        "}";
  }

  static JsonNode readWeatherBitForecastResponseTree(String json) throws JsonProcessingException {
    return objectMapper.readTree(json);
  }
}
