#!/bin/bash
export ENV=heh
export SUDO=sudo
export OPT_ARGS="-r DataPlatformHEHRGDev -s d4e5fecf-32d0-4314-a56e-ca2389ac7ac3"
#git checkout master
#git pull
./_build_deploy_image.sh
