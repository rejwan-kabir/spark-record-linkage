#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "Usage: <command> <input-path> <output-path>"
    exit 1
fi

sbt "run $1 $2"

exit 0
