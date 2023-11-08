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

