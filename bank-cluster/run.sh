#!/bin/sh

set -e
trap exit INT

env=$1
nodeId=$2

dLog="-Dconfig.file=config/$env.conf"
dNodeId="-DnodeId=$nodeId"

function network_env() {
  # edit /etc/hosts, 127.0.0.1 -> 0.0.0.0
  cat /etc/hosts | sed 's/127.0.0.1/0.0.0.0/' | tee /etc/hosts
}

function run() {
  java $dLog $dNodeId -jar bank-cluster-1.0-SNAPSHOT.jar $nodeId
}

function main() {
  network_env
  run
}

main