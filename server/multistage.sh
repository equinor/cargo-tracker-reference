#!/bin/bash

usage()
{
    echo "Usage: "
    echo "  multistage.sh -e <env> [-t <tag>] [-b <branch>] [-s]"
    echo ""
    echo "If tag and/or branch is not specified it will default to 'cargo-tracker:latest' and 'master'"
    echo "-s Prefix commands with 'sudo'"
    exit 0
}

export TAG_ARGS="-t cargo-tracking:latest"
export BRANCH=master
export ENV=

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
	-d) set -x && export DEBUG="-d"
		;;
	-s) export SUDO=sudo
		;;
	-h | --help) 
	   usage
    esac
    shift
done

$SUDO docker build -f Dockerfile.ms --target builder -t cargo-tracking:build . && \
$SUDO docker build -f Dockerfile.ms --target runtime $TAG_ARGS . && \
$SUDO resources/Deploy/manage.sh image-deploy-ms $DEBUG_FLAG -e $ENV $TAG_ARGS $DEBUG
