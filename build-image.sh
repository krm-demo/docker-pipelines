#!/bin/sh

export DOCKERFILE_NAME="Dockerfile-${DEPLOYMENT_NAME:="<<unknown-deployment>>"}"
export DOCKER_IMAGE_NAME="dpp-$DEPLOYMENT_NAME"
export DOCKER_USERNAME=${DOCKER_USERNAME:="<<unknown-docker-username>>"}
export DOCKER_PASSWORD=${DOCKER_PASSWORD:="<<unknown-docker-password>>"}

echo "Going to build and push the docker-mage with following settings:"
echo "DEPLOYMENT_NAME   = '$DEPLOYMENT_NAME'"
echo "DOCKERFILE_NAME   = '$DOCKERFILE_NAME'"
echo "DOCKER_IMAGE_NAME = '$DOCKER_IMAGE_NAME'"
echo "DOCKER_USERNAME = '$DOCKER_USERNAME'"
echo "DOCKER_PASSWORD has length " ${#DOCKER_PASSWORD}

env | sort > ./env.txt

echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin
docker build . -f $DOCKERFILE_NAME --tag $DOCKER_USERNAME/$DOCKER_IMAGE_NAME
docker push $DOCKER_USERNAME/$DOCKER_IMAGE_NAME

echo "Docker image '$DOCKER_USERNAME/$DOCKER_IMAGE_NAME' was pushed successfully"
