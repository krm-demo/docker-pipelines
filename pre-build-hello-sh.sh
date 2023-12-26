#!/bin/sh
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-sh', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
env | sort > ./env.txt
docker system info --format '{{json .}}' | jq > ./env-docker-system-info.json
