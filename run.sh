#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "Usage: <command> <input-path> <output-path>"
    exit 1
fi

spark-submit --class spark.rl.Main target/scala-2.11/spark-rl.jar "$1" "$2"

exit 0
