# Windsurfers Weather Api 

## API description

Weather forecast service. Service accepts date as an argument, fetches forecast data with WeatherBitForecastClient for each location in Location enum for the next 16 days and returns best weather forecast for surfing or null if weather is not suitable for surfing. Suitable weather for surfing: 5°C <= t <= 35°C  && 5m/s <= w <= 18m/s Best location is determined by the highest value calculated from the formula: `w * 3 + t` where `t` - temperature in °C and `w` - wind speed in m/s

## Available routes

### Get best weather forecast for windsurfing:

`GET: http://localhost:8080/api/weatherForecast/bestForWindsurfing`

### SwaggerUI:

`http://localhost:8080/api/swagger-ui`

### Actuator:

`http://localhost:8080/api/actuator`

#### Required parameters
`date` in format YYYY-MM-DD
#### Example
`http://localhost:8080/api/weatherForecast/bestForWindsurfing?date=2023-11-11`

## Build
#### local build:
`mvn clean package -DskipTests`

`java -jar -DAPP_WEATHERBIT_API_KEY=REPLACE_WITH_YOUR_KEY ./target/windsurfers-weather-api-1.0.jar`

#### docker build:

`docker build -t windsurfers-weather-api .`

`docker run -dp 8080:8080 --env APP_WEATHERBIT_API_KEY=REPLACE_WITH_YOUR_KEY windsurfers-weather-api`

#### Environmental variables:

##### Required:

`APP_WEATHERBIT_API_KEY` -> Api-key for https://api.weatherbit.io

##### Optional:

`SERVER_PORT` -> Server port, default is `8080`

`SERVER_SERVLET_CONTEXT_PATH` -> Context path, default is `/api`

`LOGGING_LEVEL_ROOT` -> Logging leve, default is `INFO`. Possible options are `FATAL|ERROR|WARN|INFO|DEBUG|TRACE|ALL|OFF`

`LOGGING_FILE_PATH` -> Directory for logs, default is `/logs`

`APP_WEATHERBIT_URL` -> URL to external WeatherBitApi service, default is `https://api.weatherbit.io/v2.0/forecast/daily`

`APP_WEATHERBIT_CONNECTION_TIMEOUT_IN_SECONDS` -> Time period within which a connection between a client and a server must be established, default is `10` seconds

`MAXIMUM_RETRIES=3` -> Amount of connection retries in cases of WeatherBitApi server errors, default is `3`

`RESPONSE_TIMEOUT_IN_SECONDS=10` -> Time period before connection with WeatherBitApi will be closed, default is `10` seconds

`ACTUATOR_ENDPOINTS` -> Active actuator endpoints, default is `metrics, health, info`

### Variables using:

#### local build:

Variables can be passed via CMD during program starting with flag `-D`, f. e.:

`java -jar -DAPP_WEATHERBIT_API_KEY=REPLACE_WITH_YOUR_KEY -DLOGGING_LEVEL_ROOT=DEBUG ./target/windsurfers-weather-api-1.0.jar`

or

Variables can be configured directly in `src/main/resources/application.yml`. Do not recommended method.

#### docker build:

Variables can be configured directly in `Dockerfile` in block ENV, f.e.:

`ENVSERVER_PORT=8080`

or

Variables can be passed via CMD during container starting with flag `--env`, f. e.:

`docker run -dp 8080:8080 --env APP_WEATHERBIT_API_KEY=REPLACE_WITH_YOUR_KEY --env LOGGING_LEVEL_ROOT=DEBUG windsurfers-weather-api`

## Additional info
*Project uses Google's coding standards. https://google.github.io/styleguide/javaguide.html

