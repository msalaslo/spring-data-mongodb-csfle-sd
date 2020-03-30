
# This is the Dockerfile reference for Spring based microservices. Multi-Stage feature is used to build image.
# 
# REQUIRED FILES TO BUILD THIS IMAGE
# ----------------------------------
# (1) JAR file containing the microservice compiled code.
# Builder Image
FROM docker-sd.faas.securitasdirect.local/maven:3.6.2-jdk-11 as build
# Required build arguments
ARG NAME
ARG VERSION
WORKDIR /workspace/app
COPY docker/$NAME-$VERSION.jar $NAME.jar
RUN jar -xf $NAME.jar

# Base Image
FROM docker-sd.faas.securitasdirect.local/pss/spring-boot-microservice:java11
# Copy classes from builder image
ARG DEPENDENCY=/workspace/app/
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${DEPENDENCY}/org/ /app

ENTRYPOINT java -XX:MaxRAMPercentage=$MAX_RAM_PERCENTAGE \
           -Djava.security.egd=file:/dev/./urandom \
           -cp app:app/lib/* com.verisure.vcp.springdatamongodbcsfle.Application -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE

           
