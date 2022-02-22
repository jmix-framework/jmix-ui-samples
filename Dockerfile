FROM openjdk:8-alpine as builder

WORKDIR application
COPY build/libs/jmix-sampler.jar jmix-sampler.jar
RUN java -Djarmode=layertools -jar jmix-sampler.jar extract

FROM openjdk:8-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./

RUN apk add --no-cache ttf-dejavu

ARG googleApiKey
ENV jmix_sampler_google_api_key=$googleApiKey

CMD java -Xmx512m org.springframework.boot.loader.JarLauncher --server.port=8080 --jmix.ui.productionMode=true \
--jmix.sampler.googleAnalyticsTracker.enabled=true --jmix.sampler.googleAnalyticsTracker.id=UA-48250949-5 \
--jmix.sampler.googleApiKey=$jmix_sampler_google_api_key


EXPOSE 8080