#!/bin/sh
# ------------------------------------------------------------------------------------
#     Build-Script for docker-image according to deployment $DEPLOYMENT_NAME
# ------------------------------------------------------------------------------------

export DOCKER_IMAGE_BUILT_AT=$(date --utc "+%FT%T.%6NZ")
export DOCKER_IMAGE_BUILT_AT_FMT=$(date -R)
export DOCKER_IMAGE_BASE_DIR=$(dirname $0)

export PRE_BUILD_SCRIPT="$DOCKER_IMAGE_BASE_DIR/pre-build-${DEPLOYMENT_NAME:="<<unknown-deployment>>"}.sh"
if [ ! -f "$PRE_BUILD_SCRIPT" ]; then
  echo "pre-build script does not exists - '$PRE_BUILD_SCRIPT'"
  exit -1
fi

export DOCKERFILE_NAME="Dockerfile-$DEPLOYMENT_NAME"
export DOCKER_IMAGE_NAME="dpp-$DEPLOYMENT_NAME"
#export DOCKER_USERNAME=${DOCKER_USERNAME:="<<unknown-docker-username>>"}
#export DOCKER_PASSWORD=${DOCKER_PASSWORD:="<<unknown-docker-password>>"}

export DOCKER_REPO_NAME=
if [ -n "$DOCKER_PASSWORD" ]; then
  echo "login to docker-registry ..."
  echo "DOCKER_USERNAME = '$DOCKER_USERNAME'"
  echo "DOCKER_PASSWORD has length " ${#DOCKER_PASSWORD}
  echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin
  if [ $? -ne 0 ]; then
    echo "cannot login to docker-registry"
    exit -2
  fi
  export DOCKER_REPO_NAME=$DOCKER_USERNAME
else
  echo "skip login to docker-registry"
fi

export DOCKER_TAG_NAME=
if [ -n "$BITBUCKET_BRANCH" ]; then
  export DOCKER_TAG_NAME="bb-$BITBUCKET_BRANCH${BITBUCKET_BUILD_NUMBER:+"-$BITBUCKET_BUILD_NUMBER"}"
elif [ -n "$BITBUCKET_COMMIT" ]; then
  export DOCKER_TAG_NAME="bb-$BITBUCKET_COMMIT"
else
  export DOCKER_TAG_NAME="local"
fi

export DOCKER_IMAGE=${DOCKER_REPO_NAME:+"$DOCKER_REPO_NAME/"}${DOCKER_IMAGE_NAME}${DOCKER_TAG_NAME:+":$DOCKER_TAG_NAME"}
echo "going to build and push the docker-mage with following settings:"
echo "DEPLOYMENT_NAME   = '$DEPLOYMENT_NAME'"
echo "DOCKERFILE_NAME   = '$DOCKERFILE_NAME'"
echo "DOCKER_REPO_NAME  = '$DOCKER_REPO_NAME'"
echo "DOCKER_IMAGE_NAME = '$DOCKER_IMAGE_NAME'"
echo "DOCKER_TAG_NAME   = '$DOCKER_TAG_NAME'"
echo "DOCKER_IMAGE      = '$DOCKER_IMAGE'"

# executing the pre-build script (compilation, preparing resources and packaging)
echo "executing the pre-build script '$PRE_BUILD_SCRIPT':"
$PRE_BUILD_SCRIPT

# building the docker-image
docker buildx build -f $DOCKERFILE_NAME --tag $DOCKER_IMAGE --platform linux/arm64/v8,linux/amd64 .
if [ $? -ne 0 ]; then
  echo "cannot build the docker-image '$DOCKER_IMAGE' with docker-file '$DOCKERFILE_NAME'"
  exit -3
fi

if [ -n "$DOCKER_PASSWORD" ]; then
  echo "push the docker-image '$DOCKER_IMAGE' to docker-registry ..."
  docker push $DOCKER_IMAGE
  if [ $? -ne 0 ]; then
    echo "cannot push the docker-image '$DOCKER_IMAGE' to docker-registry"
    exit -4
  fi
  echo "docker-image '$DOCKER_IMAGE' was pushed successfully"
else
  echo "skip pushing the docker-image into docker-registry"
fi
