#!/bin/sh
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-sh', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
env | sort > ./env.txt
echo "attempt to use 'jq' utility:"
echo '{"foo": 0, "arr": [1.2, "la-la-la", true]}' | jq
docker system info --format json | jq > ./env-docker-system-info.json
