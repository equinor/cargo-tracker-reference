FROM maven:3-jdk-11-slim AS builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY settings.xml /root/.m2/settings.xml

WORKDIR /usr/src/app

# Remove this before using cloud builds.. Freaking proxy.
# ARG MAVEN_CLI_OPTS="-X -DproxySet=true -Dhttp.proxyHost=www-proxy.statoil.no -Dhttp.proxyPort=80 -Dhttps.proxyHost=www-proxy.statoil.no -Dhttps.proxyPort=80"
ARG MAVEN_CLI_OPTS="-q"
ARG PKG_USERNAME
ARG PKG_PASSWORD

RUN mvn $MAVEN_CLI_OPTS clean compile package
#RUN cd /usr/src/app && \
#    mvn package

FROM openjdk:11
VOLUME /tmp

RUN groupadd -g 1001 -r runner && useradd -u 1001 -r -g 1001 runner
RUN mkdir -p /opt/app
COPY --from=builder /usr/src/app/target/cargo-tracker-reference-0.0.1-SNAPSHOT.jar /opt/app/app.jar
RUN chown -R runner:runner /opt/app

USER 1001

ENTRYPOINT ["java","-Xmx8196m","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/app.jar"]

