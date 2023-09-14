FROM openjdk:17-jdk-slim
MAINTAINER  viktor
COPY build/libs/secret-0.1.jar secret.jar
ENTRYPOINT ["java","-jar","/secret.jar"]
