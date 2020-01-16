#!/bin/bash


usage()
{
    echo "Usage: "
    echo "  build-complete.sh -e <env> [-t <tag>] [-b <branch>] [-s]"
    echo ""
    echo "If tag and/or branch is not specified it will default to 'latest' and 'master'"
    echo "-s will prefix certain commands with sudo"
    exit 0
}

export TAG_ARGS="-t cargotracking:latest"
export BRANCH=master
export ENV=
export SUDO=

while [ "$1" != "" ]; do
    case $1 in
        -t | --tag) shift
            export TAG_ARGS="-t $1"
            ;;
        -b | --branch) shift
           export BRANCH=$1
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

if [ -z "$ENV" ]; then
	usage
fi

resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 01-keyvault && \
resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 02-container-registry && \
./multistage.sh -e $ENV -b $BRANCH -t $TAG_ARGS

resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 03-sqlserver && \
resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 04-sqldatabase && \
resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 05-appservice $TAG_ARGS && \
resources/Deploy/manage.sh update $DEBUG_FLAG -e $ENV -a 06-appservice-webhook $TAG_ARGS
