#!/bin/bash

cd /usr/share/nginx/html/;

echo "Injecting environment variables for client runtime"
echo "# ------------------------------------------------------------ #"

# A bit of a complicated voodoo bash command, so:
# First, we find our main javascript file.
# Then, we execute bash where $1 is the filename
# Then we evaluate and run envsubst on the file, and tell it to replace CLIENT_ID and SCOPES with env variables
# Then, we pipe the transformed values into a new file with suffix .new
# Finally, we rename $1.new to $1, overwriting the original file. Success!
find . -type f -name 'main*.js' -exec sh -c 'envsubst '\''$CLIENT_ID, $REDIRECT_URI, $SCOPES, $APPINSIGHTS_INSTRUMENTATIONKEY'\'' < $1 > $1.new && mv $1.new $1' -- {} \;
cd reference/assets;
envsubst < environment.prod.json > environment.json;

echo "Successfully injected environment variables for client runtime"

echo "# ------------------------------------------------------------ #"

echo "Starting nginx"

bash /opt/bitnami/scripts/nginx/run.sh
