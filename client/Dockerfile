# Build locally with:
# docker build --build-arg USE_PROXY=true .
# Build outside corporate network:
# docker build .

# Stage 1 - build client
FROM node:alpine as builder

ARG USE_PROXY

COPY package.json yarn.lock ./
RUN if [ "$USE_PROXY" == "true" ]; then yarn config set strict-ssl false; yarn config set proxy http://www-proxy.statoil.no:80; yarn config set https-proxy http://www-proxy.statoil.no:80; fi;
RUN yarn

RUN mkdir /client && cp -R ./node_modules ./client

WORKDIR /client
COPY src ./src
COPY *.json ./

RUN $(npm bin)/ng build --prod --build-optimizer --progress=false --base-href=/reference/
#RUN $(npm bin)/ng build --progress=false

# Stage 2 - Set up Nginx
FROM bitnami/nginx

# Copy config and remove default page
COPY nginx-default.conf /opt/bitnami/nginx/conf/server_blocks/default.conf
RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /client/dist/reference /usr/share/nginx/html/reference

COPY entrypoint.sh /opt/bitnami/scripts/nginx/entrypoint.sh
# Use priveliged user to set permissions, then revert back to unpriveliged
USER 0
RUN install_packages gettext-base
RUN chown -R 1001 /usr/share/nginx/html
RUN chmod +x /opt/bitnami/scripts/nginx/entrypoint.sh
USER 1001

ENTRYPOINT ["/opt/bitnami/scripts/nginx/entrypoint.sh"]
# CMD ["nginx", "-g", "daemon off;"]


