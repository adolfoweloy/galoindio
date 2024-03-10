#!/bin/bash

./gradlew build
docker buildx build --platform linux/amd64 -t adolfoweloy/galoindio:latest .

