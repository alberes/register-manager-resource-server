FROM maven:3.8.5-openjdk-17 as build

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=build ./build/target/*.jar ./register-manager-resource-server.jar

expose 9080
expose 9081

ENV URL_OAUTH2='http://localhost:9090'
ENV URL_OAUTH2_JWKS='http://localhost:8081/oauth2/jwks'
ENV DATASOURCE_URL='jdbc:postgresql://postgresdb:5432/register_manager'
ENV DATASOURCE_USERNAME='postgres'
ENV DATASOURCE_PASSWORD='postgres'
ENV SHOW_SQL='true'
ENV DDL_AUTO='update'
ENV FORMAT_SQL='true'
ENV APP_PORT='9080'
ENV MONITORING_PORT='9081'
ENV MONITORING_LIST='*'
ENV LOG_NAME='register-manager-resource-server.log'
ENV LOG_LEVEL='warn'

ENTRYPOINT java -jar register-manager-resource-server.jar