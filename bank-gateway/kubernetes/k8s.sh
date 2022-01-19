#!/bin/sh

set -e
trap exit INT

usage() {
    echo "Usage: $0 (up|down) ..."
    echo "  up:      deploy"
    echo "  down:    delete"
}

if [ $# -ne 1 ]; then
    usage
    exit 1
fi

COMMAND=$1

kubernetes_dir=$(dirname ${BASH_SOURCE[0]})
name="order-gateway"

function up() {
   kubectl apply -f "$kubernetes_dir/$name-svc.yml"
   kubectl apply -f "$kubernetes_dir/$name-pod.yml"
}

function down() {
   kubectl delete svc "$name" || true
   kubectl delete pod "$name" || true
}

case $COMMAND in
    up)
        up
        ;;
    down)
        down
        ;;
esac