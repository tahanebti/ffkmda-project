#!/bin/bash

# Build all services
./mvnw clean install

if [ "$?" -ne 0 ]; then
    echo "Maven Clean Unsuccessful!"
    exit 1
fi