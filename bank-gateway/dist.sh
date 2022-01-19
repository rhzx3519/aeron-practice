#!/usr/bin/env bash

set -e
trap exit INT

repo="rhzx3519/"
app="order-gateway"

mvn clean install -DskipTests
docker build -t $repo$app .
docker push $repo$app