#!/bin/sh -xv
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-maven', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
$DOCKER_IMAGE_BASE_DIR/mvnw clean package
