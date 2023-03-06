#! /bin/sh

IMAGE_VERSION=26.0
APPLICATION_NAME=authentication-application

export JAVA_HOME=/Users/evangelosvatikiotis/Library/Java/JavaVirtualMachines/azul-11.0.16/Contents/Home
mvn clean install -f ../pom.xml
docker build -t vaggelas/${APPLICATION_NAME}:${IMAGE_VERSION} ../
docker push vaggelas/${APPLICATION_NAME}:${IMAGE_VERSION}

#docker run --name ${APPLICATION_NAME}-${IMAGE_VERSION} \
#  -p 8080:8080 \
#  -e "domain.name=localhost" \
#  vaggelas/${APPLICATION_NAME}:${IMAGE_VERSION}