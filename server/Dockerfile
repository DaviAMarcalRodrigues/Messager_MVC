FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/
COPY server/pom.xml server/
COPY client/pom.xml client/
RUN mvn -pl server -am dependency:go-offline -B

COPY common/src common/src
COPY server/src server/src
RUN mvn -pl server -am package -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/server/target/server.jar server.jar
EXPOSE 5000
CMD ["java", "-jar", "server.jar"]