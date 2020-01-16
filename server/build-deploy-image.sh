#!/bin/bash

usage()
{
    echo "Usage: "
    echo "  build-deploy-image.sh -e <env> [-t <tag>] [-b <branch>] [-s]"
    echo ""
    echo "If tag and/or branch is not specified it will default to 'latest' and 'master'"
    echo "-s Prefix commands with 'sudo'"
    exit 0
}

export TAG_ARGS="-t latest"
export BRANCH=master

while [ "$1" != "" ]; do
    case $1 in
        -t | --tag) shift
            TAG_ARGS="-t $1"
            ;;
    	-b | --branch) shift
     	   BRANCH=$1
	   ;;
	-e | --env) shift
	   export ENV=$1
	   ;;
	-s) export SUDO=sudo
		;;
	-h | --help) 
	   usage
    esac
    shift
done

git checkout $BRANCH
git pull

mvn clean compile && \
cd client && \
yarn && \
yarn deploy && \
cd .. && \
mvn package && \

$SUDO resources/Deploy/manage.sh image-build $DEBUG_FLAG -e $ENV $OPT_ARGS $TAG_ARGS && \
$SUDO resources/Deploy/manage.sh image-deploy $DEBUG_FLAG -e $ENV $OPT_ARGS $TAG_ARGS
