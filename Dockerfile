FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/windsurfers-weather-api-1.0.jar .
#ENV APP_WEATHERBIT_API_KEY=XXXXXXXXXXXX
#ENV SERVER_PORT=8080
#ENV SERVER_SERVLET_CONTEXT_PATH=/api
#ENV LOGGING_LEVEL_ROOT=INFO
#ENV APP_WEATHERBIT_URL=https://api.weatherbit.io/v2.0/forecast/daily
#ENV APP_WEATHERBIT_CONNECTION_TIMEOUT_IN_SECONDS=10
#ENV MAXIMUM_RETRIES=3
#ENV RESPONSE_TIMEOUT_IN_SECONDS=10
#ENV ACTUATOR_ENDPOINTS=*
CMD ["java", "-jar", "windsurfers-weather-api-1.0.jar"]