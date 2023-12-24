#!/bin/sh
echo "~~~~~~~~ $(realpath --relative-to . $0) ~~~~~~~~"
echo
$(dirname "$0")/hello.sh
