FROM openjdk:21-slim AS build

WORKDIR /usr/src/app

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests

FROM openjdk:21-slim

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/api-payments

COPY --from=build /usr/src/app/target/api-payments*.jar api-payments.jar

EXPOSE 5006
EXPOSE 8084

CMD java ${ADDITIONAL_OPTS} -jar api-payments.jar --spring.profiles.active=${PROFILE}