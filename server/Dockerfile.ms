FROM ubuntu:18.04 AS builder
RUN apt-get update && apt-get install -y \
    default-jdk \
    maven \
    git \
    curl \
    gnupg \
    lsb-release
RUN AZ_REPO=$(lsb_release -cs) && \
    echo "deb [arch=amd64] https://packages.microsoft.com/repos/azure-cli/ $AZ_REPO main" | tee /etc/apt/sources.list.d/azure-cli.list && \
    curl -L https://packages.microsoft.com/keys/microsoft.asc | apt-key add - && \
    apt-get update && \
    apt-get install -y apt-transport-https azure-cli
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app


RUN mvn -f /usr/src/app/pom.xml clean compile
RUN cd /usr/src/app && \
    mvn package

FROM openjdk:11 AS runtime
VOLUME /tmp
COPY --from=builder /usr/src/app/target/cargo-tracker-reference-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-Xmx8196m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
