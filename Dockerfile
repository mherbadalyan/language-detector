FROM gradle:jdk17-focal as cache
RUN mkdir -p /home/gradle/cache
ENV GRADLE_USER_HOME /home/gradle/cache
WORKDIR /home/gradle/app
COPY build.gradle gradle.properties settings.gradle /home/gradle/app/
RUN gradle init -i --stacktrace

FROM gradle:jdk17-focal as builder
COPY --from=cache /home/gradle/cache /home/gradle/.gradle
COPY ./ /opt/app
WORKDIR /opt/app
RUN gradle build

FROM eclipse-temurin:17-jre-focal
COPY --from=builder /opt/app/build/libs/* /deployments/lib/
COPY --from=builder /opt/app/build/*-runner.jar /deployments/quarkus-run.jar

USER 185
EXPOSE 8080

ENTRYPOINT java -jar /deployments/quarkus-run.jar
