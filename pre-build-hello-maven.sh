#!/bin/sh
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-maven', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
./mvnw clean package
