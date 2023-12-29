#!/bin/sh
echo "~~~~~~~~ $(realpath --relative-to . $0) ~~~~~~~~"

DOCKER_TAG_NAME="gh-main-11"
if [ "$DOCKER_TAG_NAME" == "local" ]; then
  echo "'$DOCKER_TAG_NAME' is NOT a tag of a locally created docker-image"
else
  echo "'$DOCKER_TAG_NAME' is a tag of a locally created docker-image"
fi
DOCKER_IMAGE="krmdemo/dpp-hello-sh:$DOCKER_TAG_NAME"

GITHUB_STEP_SUMMARY=$(mktemp) || exit 1

echo "
\`\`\`bash
    docker run --rm --pull always $DOCKER_IMAGE
\`\`\`" >> $GITHUB_STEP_SUMMARY

cat $GITHUB_STEP_SUMMARY
rm -f -- "$GITHUB_STEP_SUMMARY"
