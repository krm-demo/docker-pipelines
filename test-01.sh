#!/bin/sh
echo "~~~~~~~~ $(realpath --relative-to . $0) ~~~~~~~~"
echo

export REPO_NAME=
export IMAGE_NAME=
export TAG_NAME=
echo "0) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
#export IMAGE=${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"

export REPO_NAME=
export IMAGE_NAME=img
export TAG_NAME=
echo "1) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
#export IMAGE=${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"

export REPO_NAME=my-repo
export IMAGE_NAME=image
export TAG_NAME=
echo "2) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
#export IMAGE=${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"

export REPO_NAME=my-repo
export IMAGE_NAME=image
export TAG_NAME=la-la-la
echo "3) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
#export IMAGE=${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"

export REPO_NAME=
export IMAGE_NAME=image
export TAG_NAME=ka-ka-ka
echo "4) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
#export IMAGE=${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"

export REPO_NAME=some-repo
export IMAGE_NAME=
export TAG_NAME=ka-ka-ka
echo "5) REPO_NAME = '$REPO_NAME'; IMAGE_NAME = '$IMAGE_NAME'; TAG_NAME = '$TAG_NAME'"
export IMAGE=${IMAGE_NAME:+${REPO_NAME:+"$REPO_NAME/"}${IMAGE_NAME}${TAG_NAME:+":$TAG_NAME"}}
echo "   IMAGE = '$IMAGE'"
echo "------------------------------"
