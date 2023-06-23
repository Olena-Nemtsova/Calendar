FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY . .

RUN ./gradlew build

ENTRYPOINT java -jar build/libs/Calendar.jar
