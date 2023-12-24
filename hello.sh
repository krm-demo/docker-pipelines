#!/bin/sh
echo "Hello from '$(realpath "$0")' at '$(date -R)'"
echo "================================================="
echo "runtime-environment on start-up:"
echo "-------------------------------------------------"
env | sort
echo "================================================="
echo "build-environment when building the docker image:"
echo "-------------------------------------------------"
cat ./env.txt
echo "================================================="
