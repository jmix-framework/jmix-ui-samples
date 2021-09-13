FROM openjdk:11-alpine as builder

WORKDIR application
COPY build/libs/jmix-sampler.jar jmix-sampler.jar
RUN java -Djarmode=layertools -jar jmix-sampler.jar extract

FROM openjdk:11-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./

RUN apk add --no-cache ttf-dejavu

CMD java -Xmx512m org.springframework.boot.loader.JarLauncher --server.port=8080 --jmix.sampler.googleAnalyticsTracker.enabled=true --jmix.sampler.googleAnalyticsTracker.id=UA-48250949-5

EXPOSE 8080