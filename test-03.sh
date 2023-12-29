#!/bin/sh
echo "~~~~~~~~ $(realpath --relative-to . $0) ~~~~~~~~"

DOCKER_IMAGE="krmdemo/dpp-hello-sh:gh-main-11"
GITHUB_STEP_SUMMARY=$(mktemp) || exit 1

echo "
\`\`\`bash
    docker run --rm --pull always $DOCKER_IMAGE
\`\`\`" >> $GITHUB_STEP_SUMMARY

cat $GITHUB_STEP_SUMMARY
rm -f -- "$GITHUB_STEP_SUMMARY"
