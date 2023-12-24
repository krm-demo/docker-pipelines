#!/bin/sh
# ------------------------------------------------------------------------------------
#   Pre-Build script for deployment-type 'hello-java', invoked from 'build-image.sh'
# ------------------------------------------------------------------------------------
env | sort | sed 's/\\/\\\\/g' > ./env__esc_bs.txt   # <-- double back-slash to read with java.util.Properties
javac --source-path "${DOCKER_IMAGE_BASE_DIR:-$(dirname $0)}" Hello.java
