FROM amazoncorretto:21-alpine

COPY build/libs/jmix-ui-samples.jar jmix-ui-samples.jar

ENTRYPOINT ["java", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/app/heap-dumps", "-jar", "/jmix-ui-samples.jar"]
