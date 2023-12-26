#!/bin/sh
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-sh', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
env | sort > ./env.txt
echo "attempt to use 'jq' utility:"
echo '{"foo": 0, "arr": [1.2, "la-la-la", true]}' | jq
echo "attempt to dump docker system-info:"
echo "-----------------------------------"
docker system info --format json
echo "-----------------------------------"
echo "saving docker system-info into './env-docker-system-info.json' ..."
docker system info --format json | jq > ./env-docker-system-info.json
