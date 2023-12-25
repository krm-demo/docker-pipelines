#!/bin/sh
echo "Hello from '$(realpath "$0")' at '$(date -R)'"
echo "================================================="
echo "runtime-environment on start-up:"
echo "-------------------------------------------------"
echo '$(uname --kernel-name)      = '$(uname --kernel-name)
echo '$(uname --kernel-release)   = '$(uname --kernel-release)
echo '$(uname --operating-system) = '$(uname --operating-system)
echo '$(uname --machine)          = '$(uname --machine)
echo '$(uname --all) = '$(uname --all)
env | sort
echo "================================================="
echo "build-environment when building the docker image:"
echo "-------------------------------------------------"
cat ./env.txt
echo "================================================="
