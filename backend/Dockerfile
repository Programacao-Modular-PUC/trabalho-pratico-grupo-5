# our base build image (eclipse-temurin replaces removed docker.io/library/openjdk / old maven jdk images)
FROM maven:3.9-eclipse-temurin-8 AS maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package -DskipTests

# our final base image
FROM eclipse-temurin:8-jre-alpine

# set deployment directory
WORKDIR /my-project

# copy over the built artifact from the maven image
COPY --from=maven target/sistema-hospedagem-1.0.jar ./

CMD ["java", "-jar", "./sistema-hospedagem-1.0.jar"]
