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
name="order-cluster"

function up() {
   for i in `seq 0 2`; do
     kubectl apply -f "$kubernetes_dir/$name$i-svc.yml"
   done

   for i in `seq 0 2`; do
     kubectl apply -f "$kubernetes_dir/$name$i-pod.yml"
   done
}

function down() {
   for i in `seq 0 2`; do
     kubectl delete svc "$name$i" || true
     kubectl delete pod "$name$i" || true
   done
}

case $COMMAND in
    up)
        up
        ;;
    down)
        down
        ;;
esac