FROM maven:3-jdk-11-slim AS builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app

# Remove this before using cloud builds.. Freaking proxy.
# ARG MAVEN_CLI_OPTS="-DproxySet=true -Dhttp.proxyHost=www-proxy.statoil.no -Dhttp.proxyPort=80 -Dhttps.proxyHost=www-proxy.statoil.no -Dhttps.proxyPort=80"

# RUN mvn $MAVEN_CLI_OPTS clean compile package
RUN mvn clean compile package
#RUN cd /usr/src/app && \
#    mvn package

FROM openjdk:11
VOLUME /tmp
COPY --from=builder /usr/src/app/target/cargo-tracker-reference-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-Xmx8196m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

