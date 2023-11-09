# Windsurfers Weather Api 

## API description

Weather forecast service. Service accepts date as an argument, fetches forecast data with WeatherBitForecastClient for each location in Location enum for the next 16 days and returns best weather forecast for surfing or null if weather is not suitable for surfing. Suitable weather for surfing: 5°C <= t <= 35°C  && 5m/s <= w <= 18m/s Best location is determined by the highest value calculated from the  formula: w * 3 + t where t - temperature in °C and w - wind speed in m/s

## Available routes

`GET: http://localhost:8080/api/weatherForecast/bestForWindsurfing`
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

##### required:

`APP_WEATHERBIT_API_KEY` -> api-key for https://api.weatherbit.io

##### Optional(=default_value):

`SERVER_PORT=8080` ->

`SERVER_SERVLET_CONTEXT_PATH=/api` ->

`LOGGING_LEVEL_ROOT=INFO` ->

`APP_WEATHERBIT_URL=https://api.weatherbit.io/v2.0/forecast/daily` ->

`APP_WEATHERBIT_CONNECTION_TIMEOUT_IN_SECONDS=10` ->

`MAXIMUM_RETRIES=3` ->

`RESPONSE_TIMEOUT_IN_SECONDS=10` -> 


