FROM openjdk:17 as builder

WORKDIR /app

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo Asia/Seoul > /etc/timezone

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

COPY src/main/resources/static /app/static

ENTRYPOINT ["java", "-jar", "app.jar"]