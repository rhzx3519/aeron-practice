#!/bin/sh
set -e
trap exit INT

env=$1

dConfig="-Dconfig.file=config/$env.conf"

function network_env() {
  # edit /etc/hosts, 127.0.0.1 -> 0.0.0.0
  cat /etc/hosts | sed 's/127.0.0.1/0.0.0.0/' | tee /etc/hosts
}

function run() {
  java $dConfig -jar bank-gateway-1.0-SNAPSHOT.jar
}

function main() {
  network_env
  run
}

main