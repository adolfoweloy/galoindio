FROM amazoncorretto:21-alpine-jdk

# header
MAINTAINER Sr. Eloy

# preparing the workdir
RUN mkdir -p /opt/rinhabackend
WORKDIR /opt/rinhabackend

# building the project
ADD build/libs/rinhabackend-0.0.1-SNAPSHOT.jar rinha.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "rinha.jar"]
