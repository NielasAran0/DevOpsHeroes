#!/bin/bash

cd $1-service
mvn package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/$1-service .
minikube kubectl -- delete deployment $1-service
minikube kubectl -- apply -f kubernetes/

