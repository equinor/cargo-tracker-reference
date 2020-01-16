FROM node:alpine as clientbuilder

COPY client/package.json client/yarn.lock ./
RUN yarn

RUN mkdir /client && cp -R ./node_modules ./client

WORKDIR /client
COPY client/src ./src
COPY client/*.json ./

RUN $(npm bin)/ng build --configuration=prod --progress=false

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
COPY --from=clientbuilder /client/dist/cargo-tracking-web /usr/src/app/target/classes/static
RUN cd /usr/src/app && \
    mvn package

FROM openjdk:11 AS runtime
VOLUME /tmp
COPY --from=builder /usr/src/app/target/cargo-tracking-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-Xmx8196m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
