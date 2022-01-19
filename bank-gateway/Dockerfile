FROM docker.io/library/openjdk:8-alpine

WORKDIR /app
COPY config ./config
COPY --chmod=755 target/bank-gateway-1.0-SNAPSHOT.jar .
COPY --chmod=755 run.sh .

ENTRYPOINT ["./run.sh"]