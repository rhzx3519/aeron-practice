FROM docker.io/library/openjdk:8-alpine

WORKDIR /app
COPY --chmod=755 target/bank-cluster-1.0-SNAPSHOT.jar .
COPY --chmod=755 run.sh .
COPY --chmod=755 aeron ./aeron
COPY config ./config

ENTRYPOINT ["./run.sh"]