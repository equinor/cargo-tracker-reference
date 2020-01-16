#!/bin/bash

environment=
op=
asset=
servicePrincipal=
specifiedResourceGroup=
specifiedSubscription=
debug=false

usage()
{
    echo "Usage: "
    echo "  manage update -e <environment> [-a <asset>] [-r <resource group>] [-s <subscription id>]"
    echo "  manage teardown -e <environment> [-a <asset>] [-r <resource group>] [-s <subscription id>]"
    echo "  manage show -e <environment> -a <asset> [-r <resource group>] (show deployment file of asset)"
    echo ""
    echo "Environments: supported (or to-be) by the script:" 
    echo "	dev, staging, test, prod, (or custom dev environment)"
    echo ""
    echo "Custom dev environments may be specified by creating template parameter files, following the naming standard convention."
    echo " e.g. if you wanted to create a dev environment called niand, create a file called keyvault-parameters-niand.json"
    echo "  if no such file exist, the script will fall back to the keyvault-parameters-dev.json file"
    echo "  This is useful for example to override a url (which must be unique)"
    echo "  These additional environments will only override \"dev\""
    echo ""
    echo " -e update/teardown/show the specified environment"
    echo " -a update/teardown/show the specified environment/deployment"
    echo " -r override the default resource group, with the specfied, e.g. load \"dev\" into DataPlatformNIANDRGDev (only for dev)"
    echo "    Note that you may need to give the dev ServicePrincipal contributor access to the resource group"
    echo " -s override the default subscription, examples:"
    echo "    VanDamme: d4e5fecf-32d0-4314-a56e-ca2389ac7ac3"
    echo "    Omnia Applications Non prod: 4bb8df6f-e5b7-45f8-8db6-22ec061d6938"
    echo " -d Debug mode"
    echo ""
    echo "Assets: corresponds to the subfolder, i.e. 'manage update -e dev -a 01-keyvault' will deploy the keyvault only to the dev environment"
    echo "        If the asset is not specified, then all assets will be updated or removed."
    echo ""
    echo "Service Principal passwords are picked up from the environment. Locally, you can create a file called ~/.azure_pass to contain these passwords."
    echo "Note that this file needs access 600, and must never be checked in to source control. A build server will typically have another mechanism to set"
    echo "the environment variables. The variable names are of the format AZ_PASS_<env>=xxxx. E.g. AZ_PASS_dev for the dev environment."
    echo ""
    exit 0
}

while [ "$1" != "" ]; do
    case $1 in
        -e | --environment) shift
            environment=$1
            ;;
        -a | --asset) shift
	    asset=$1
	    asset=${asset%/}
	    ;;
        -h | --help ) usage
            ;;
	-r | --resource-group) shift
		specifiedResourceGroup=$1
		;;
	-d | --debug) 
		debug=true
		;;
	-t | --tag) shift
		tag=$1
		;;
	-s | --subscription) shift
		specifiedSubscription=$1
		;;
	    update) op=update
				;;
	    teardown) op=teardown
				;;
	    show)   op=show
				;;
	    image-build) op=image-build
				;;
	    image-deploy) op=image-deploy
				;;
	    image-pull) op=image-pull
				;;
	    image-deploy-ms) op=image-deploy-ms
		    	;;
        *)  usage
    esac
    shift
done

if [ "$debug" = true ]; then
        set -x
fi

if [ ! "$environment" = "dev" ] && [ ! "$environment" = "staging" ] && [ ! "$environment" = "prod" ] && [ ! "$environment" == "test" ]; then
	if [ -z "$specifiedResourceGroup" ] || [ -z "$specifiedSubscription" ]; then
		echo "When using a non-standard environment, a resource group and subscription must be specified"
		exit 1;
	fi
fi

if [ "$op" = "show" ] && [ -z "$asset" ]; then
	echo "When showing a deployment, an asset must be specified"
	exit 1
fi

idUri=
baseUrl=
azurePassword=

if [ -f ~/.azure_pass ]; then
     if [ ! `stat -c %a ~/.azure_pass` == "600" ]; then
          echo "./azure_pass must be set to 600"
          exit 1
     fi
     source ~/.azure_pass
fi

case $environment in
	staging) resourceGroup=CTRGDevRGTest
                 subscription=4bb8df6f-e5b7-45f8-8db6-22ec061d6938
                 servicePrincipal=http://lid-bitbucket-pipeline-dev
                 servicePrincipalId=8fcb096c-c5ca-406f-ab72-332c179e710a
				kvExternalName=ct-kv-external-staging
                ;;
        prod) resourceGroup=CCTRGProd
				subscription=cb734de0-83f0-453d-8eca-9e52ecf9c897
				servicePrincipal=api://27cc9ee6-58d6-43b9-955a-dcaec9769a99
				servicePrincipalId=27cc9ee6-58d6-43b9-955a-dcaec9769a99
				kvExternalName=ct-kv-external
				azurePassword=$AZ_PASS_prod
                ;;
	test) resourceGroup=CCTRGTest
              subscription=8f4920e7-d1a4-470f-ac60-d14467d00350
              servicePrincipal=https://StatoilSRM.onmicrosoft.com/e08bad68-c8e7-4cbc-943b-7da855a098bc
              servicePrincipalId=18f46b40-3cb3-4bd4-b530-ab95114adb99
	      kvExternalName=ct-kv-external-test
		  azurePassword=$AZ_PASS_test
		;;
	dev)  resourceGroup=CCTRGDev
              subscription=8f4920e7-d1a4-470f-ac60-d14467d00350
              servicePrincipal=https://StatoilSRM.onmicrosoft.com/c521d49f-08bf-4912-98f8-b342052cedee
              servicePrincipalId=b4c9b7b8-090f-42e0-9d32-4a1fce2e26c2
	      kvExternalName=ct-kv-external-dev
		  azurePassword=$AZ_PASS_dev
                ;;
	*)  resourceGroup=$specifiedResourceGroup
            subscription=$specifiedSubscription
            servicePrincipal=http://lid-bitbucket-pipeline-dev
            servicePrincipalId=8fcb096c-c5ca-406f-ab72-332c179e710a
			azurePassword=$AZ_PASS_default
		;;
esac

if [ -z "$op" ]; then
	usage
	exit 1
fi

# srcDir is the directory where this script is located
srcDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

if [ ! -z "$servicePrincipal" ]; then
    az login --service-principal --username $servicePrincipalId --password $azurePassword --tenant 3aa4a235-b6e2-48d5-9195-7fcf05b459b0
    az account set --subscription $subscription
    echo "Performing azure operations as $servicePrincipal ($servicePrincipalId)"
else
    echo -n "Performing azure operations as "
    az account show
fi


# $1 - asset name
# $2 - deployment name
# $3 - extra parameters
#
update() {
	echo -n "Creating or updating asset $1....."
	assetDir=$1
	extraParams=${@:3}
	shortName=${assetDir:3}
        deploymentName=$2
	cd $srcDir/$assetDir

	paramFile=$shortName"-parameters-"$environment".json"
	if ! [ -f "$paramFile" ]; then
		echo -n $paramFile" not found, falling back to "
		paramFile=$shortName"-parameters-dev.json"
		echo $paramFile
	fi

	az group deployment create --name $deploymentName --resource-group \
		$resourceGroup --template-file $shortName"-template.json" --parameters $paramFile $extraParams 
	if ! [ $? -eq 0 ]; then
		echo "failed"
		cd ..
		exit 1
	fi
	cd ..
	echo "Ok"
	return 0
}


# $1 asset
# $2 deployment name
# $3 id query
teardown() {
	echo -n "Removing asset $1....."
	assetDir=$1
	shortName=${assetDir:3}
	cd $srcDir/$assetDir
    deploymentName=$2
	idQuery=$3
	assetId=`az group deployment show -n $deploymentName --resource-group $resourceGroup --query $idQuery` 
	if  ! [ $? -eq 0 ]; then 
		echo "Failed to lookup deployment $deploymentName"
		cd ..
		return 1
	fi
	assetId=`sed -e  's/^"//' -e 's/"$//' <<<$assetId`
	allowAutoTeardown=`az resource show --ids $assetId --query tags.allowAutoTeardown`
    allowAutoTeardown=`sed -e  's/^"//' -e 's/"$//' <<<$allowAutoTeardown`
	if [ "${allowAutoTeardown,,}" == "true" ] && ! [ -z "$assetId" ]; then
        az resource delete --ids $assetId 
        if ! [ $? -eq 0 ]; then
            echo "failed."
            exit 1
        fi
	    echo "Ok"	
    else
	   echo "missing resource or \"allowAutoTeardown\" tag... skipping."
    fi
	cd ..
}

if [ "$op" == "update" ]; then

    if [ -z "$asset" ] && [ -z "$tag" ]; then
        echo "Docker image tag must be specified (-t <tag>)"
	exit 1
    fi

    if [ -z "$asset" ] || [ "$asset" == "01-keyvault" ]; then
        deploymentName="CargoTracking.KeyVault-"$environment
	update "01-keyvault" $deploymentName
        if [ ! -z "$servicePrincipal" ]; then
            echo -n "   Assigning secret access to service principal $servicePrincipal..."
            kvName=`az group deployment show -n $deploymentName --resource-group $resourceGroup --query properties.outputs.name.value`
            kvName=`sed -e  's/^"//' -e 's/"$//' <<<$kvName`
            az keyvault set-policy --secret-permissions get set --spn $servicePrincipal --name $kvName
            echo "Ok" 
        fi           
    fi

    keyVaultDeployment="CargoTracking.Keyvault-"$environment
    kvName=`az group deployment show -n $keyVaultDeployment --resource-group $resourceGroup --query properties.outputs.name.value`
    kvName=`sed -e  's/^"//' -e 's/"$//' <<<$kvName`

    if [ -z "$asset" ] || [ "$asset" == "02-container-registry" ]; then
        if [ -z "$kvName" ]; then
            echo "Cannot deploy container registry  without keyvault"
            exit 1
        fi
        deploymentName="CargoTracking.ContainerRegistry-"$environment 
	update "02-container-registry" $deploymentName
	if ! [ -z "$servicePrincipal" ]; then
		echo -n "   Assigning contributor role to service principal $servicePrincipal..."
		registryId=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.outputs.registryId.value`
		registryId=`sed -e  's/^"//' -e 's/"$//' <<<$registryId`
		az role assignment create --assignee $servicePrincipal --scope $registryId --role=contributor 
		echo "Ok"
	fi
        acrPassword=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.outputs.registryPassword.value`
        acrUser=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.outputs.registryUsername.value`
        acrPassword=`sed -e  's/^"//' -e 's/"$//' <<<$acrPassword`
        acrUser=`sed -e  's/^"//' -e 's/"$//' <<<$acrUser`
        az keyvault secret set --vault-name $kvName --name ACR-PASSWORD --value $acrPassword
        az keyvault secret set --vault-name $kvName --name ACR-USER --value $acrUser        
    fi

    if [ -z "$asset" ] || [ "$asset" == "03-sqlserver" ]; then
	if [ -z "$kvName" ]; then
           echo "Cannot deploy SQL server without keyvault"
	   exit 1
	fi
	adminUserPassword=`az keyvault secret show --vault-name $kvName --name DB-SERVER-ADMIN-PASSWORD --query value`
        adminUserPassword=`sed -e  's/^"//' -e 's/"$//' <<<$adminUserPassword`
	newPassword=false
	if [ -z "$adminUserPassword" ]; then
		echo "SQL Server admin password not found in keyvault, generating new"
		adminUserPassword=`openssl rand -base64 32`"!xH0"
		newPassword=true
	fi
        deploymentName="CargoTracking.SQLServer-$environment"
	extraParams="--parameters adminPassword=$adminUserPassword"

	update "03-sqlserver" $deploymentName $extraParams
	if [ $? -eq 0 ] && [ "$newPassword" = true ]; then
		echo "Storing SQL Server admin password in KeyVault"
		az keyvault secret set --vault-name $kvName --name DB-SERVER-ADMIN-PASSWORD --value $adminUserPassword
	fi
    fi

    if [ -z "$asset" ] || [ "$asset" == "04-sqldatabase" ]; then
        if [ -z "$kvName" ]; then
            echo "Cannot deploy SQL database without keyvault"
            exit 1
        fi
        serverDeployment="CargoTracking.SQLServer-$environment"
        serverName=`az group deployment show -n $serverDeployment --resource-group $resourceGroup --query properties.dependencies[0].dependsOn[0].resourceName`
        serverName=`sed -e  's/^"//' -e 's/"$//' <<<$serverName`
        if [ -z "$serverName" ]; then
            echo "Cannot deploy database without database server. Expected server deployment called: $serverDeployment"
            echo 1
        fi
        extraParams="--parameters serverName=$serverName"
        deploymentName="CargoTracking.SQLDatabase-$environment"
        update "04-sqldatabase" $deploymentName $extraParams
        appUserPassword=`az keyvault secret show --vault-name $kvName --name DB-APP-PASSWORD --query value`
        appUserPassword=`sed -e  's/^"//' -e 's/"$//' <<<$appUserPassword`
        if [ -z "$appUserPassword" ]; then
            echo "App user database password not found, generating new"
            appUserPassword=`openssl rand -base64 32`"!xH0"
            az keyvault secret set --vault-name $kvName --name DB-APP-PASSWORD --value $appUserPassword
        fi
    fi

    if [ -z "$asset" ] || [ "$asset" == "05-appservice" ]; then
        if [ -z "$kvName" ]; then
            echo "Cannot deploy AppService database without keyvault"
            exit 1
        fi

	if [ -z "$tag" ]; then
		echo "The docker image tag must be specified (-t <tag>)"
		exit 1
	fi

 	idUri=$servicePrincipal
        deploymentName="CargoTracking.AppService-"$environment
        appUserPassword=`az keyvault secret show --vault-name $kvName --name DB-APP-PASSWORD --query value`
        appUserPassword=`sed -e  's/^"//' -e 's/"$//' <<<$appUserPassword`
        adminUserPassword=`az keyvault secret show --vault-name $kvName --name DB-SERVER-ADMIN-PASSWORD --query value`
        adminUserPassword=`sed -e  's/^"//' -e 's/"$//' <<<$adminUserPassword`
        acrPassword=`az keyvault secret show --vault-name $kvName --name ACR-PASSWORD --query value`
        acrPassword=`sed -e  's/^"//' -e 's/"$//' <<<$acrPassword`
        sasSportKey=`az keyvault secret show --vault-name $kvName --name SB-SPORT-SAS-KEY --query value`
        sasSportKey=`sed -e  's/^"//' -e 's/"$//' <<<$sasSportKey`
        sasSportKeyName=`az keyvault secret show --vault-name $kvName --name SB-SPORT-SAS-KEY-NAME --query value`
        sasSportKeyName=`sed -e  's/^"//' -e 's/"$//' <<<$sasSportKeyName`
        extraParams="--parameters appSettingDbAppPassword=$appUserPassword \
                                  appSettingDbServerAdminPassword=$adminUserPassword \
                                  appSettingDockerRegistryServerPassword=$acrPassword \
                                  appSettingSbSportSasKey=$sasSportKey \
			  	  dockerImageTag=$tag \
                                  appSettingSbSportSasKeyName=$sasSportKeyName"
        update "05-appservice" $deploymentName $extraParams
        appName=`az group deployment show --resource-group $resourceGroup --name $deploymentName --query properties.dependencies[0].resourceName`
        appName=`sed -e  's/^"//' -e 's/"$//' <<<$appName`
        # Config needed for webhook
        publishingUserName=`az group deployment show --resource-group $resourceGroup --name $deploymentName --query properties.outputs.publishingUserName.value`
        publishingPassword=`az group deployment show --resource-group $resourceGroup --name $deploymentName --query properties.outputs.publishingPassword.value`
        publishingUserName=`sed -e  's/^"//' -e 's/"$//' <<<$publishingUserName`
        publishingPassword=`sed -e  's/^"//' -e 's/"$//' <<<$publishingPassword`
        az keyvault secret set --vault-name $kvName --name APP-SERVICE-PUBLISHING-USER --value $publishingUserName
        az keyvault secret set --vault-name $kvName --name APP-SERVICE-PUBLISHING-PASSWORD --value $publishingPassword
    fi

    if [ -z "$asset" ] || [ "$asset" == "06-appservice-webhook" ]; then

	if [ -z "$tag" ]; then
                echo "The docker image tag must be specified (-t <tag>)"
                exit 1
        fi


        publishingUserName=`az keyvault secret show --vault-name $kvName --name APP-SERVICE-PUBLISHING-USER --query value`
        publishingUserName=`sed -e  's/^"//' -e 's/"$//' <<<$publishingUserName`
        publishingPassword=`az keyvault secret show --vault-name $kvName --name APP-SERVICE-PUBLISHING-PASSWORD --query value`
        publishingPassword=`sed -e  's/^"//' -e 's/"$//' <<<$publishingPassword`
       
        deploymentName="CargoTracking.AppService-"$environment 
        siteName=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.parameters.siteName.value`
        siteName=`sed -e  's/^"//' -e 's/"$//' <<<$siteName`
        imageName=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.parameters.dockerImageName.value`
        imageName=`sed -e  's/^"//' -e 's/"$//' <<<$imageName`
	imageTag=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.parameters.dockerImageTag.value`
        imageTag=`sed -e  's/^"//' -e 's/"$//' <<<$imageTag`

        deploymentName="CargoTracking.ContainerRegistry-"$environment
        registryName=`az group deployment show -g $resourceGroup -n $deploymentName --query properties.parameters.registryName.value`
        registryName=`sed -e  's/^"//' -e 's/"$//' <<<$registryName`

        extraParams="--parameters publishingUserName=$publishingUserName \
                                  publishingPassword=$publishingPassword \
                                  siteName=$siteName \
                                  webhookName="ctwebhook"$environment \
                                  dockerImageName=$tag \
                                  registryName=$registryName"

        deploymentName="CargoTracking.AppServiceWebhook-"$environment
        update "06-appservice-webhook" $deploymentName $extraParams
    fi

	exit 0
fi


if [ "$op" == "teardown" ]; then
    if [ -z "$asset" ] || [ "$asset" == "06-appservice-webhook" ]; then
        teardown "06-appservice-webhook" "CargoTracking.AppServiceWebhook-$environment" "properties.outputResources[0].id"
    fi
    if [ -z "$asset" ] || [ "$asset" == "05-appservice" ]; then
	# Deletion of serverfarms was quirky, hence the special handling
	echo "Removing 05-appservice"
        source $srcDir/05-appservice/appservice-appreg-parameters-$environment.env
	assetId=`az group deployment show -n CargoTracking.AppService-$environment --resource-group $resourceGroup --query properties.outputResources[1].id`
	assetId=`sed -e  's/^"//' -e 's/"$//' <<<$assetId`
	az webapp delete --ids $assetId
	assetId=`az group deployment show -n CargoTracking.AppService-$environment --resource-group $resourceGroup --query properties.outputResources[0].id`
	assetId=`sed -e  's/^"//' -e 's/"$//' <<<$assetId`
	az appservice plan delete --yes --ids $assetId
    fi
	if [ -z "$asset" ] || [ "$asset" == "04-sqldatabase" ]; then
        teardown "04-sqldatabase" "CargoTracking.SQLDatabase-$environment" "properties.outputResources[0].id"
    fi
    if [ -z "$asset" ] || [ "$asset" == "03-sqlserver" ]; then
		teardown "03-sqlserver" "CargoTracking.SQLServer-$environment" "properties.outputResources[0].id"
    fi
	if [ -z "$asset" ] || [ "$asset" == "02-container-registry" ]; then
		teardown "02-container-registry" "CargoTracking.ContainerRegistry-$environment" "properties.outputs.registryId.value"
	fi
    if [ -z "$asset" ] || [ "$asset" == "01-keyvault" ]; then
		teardown "01-keyvault" "CargoTracking.KeyVault-$environment" "properties.outputResources[0].id"
    fi
    exit 0
fi

if [ "$op" == "show" ]; then
	shortName=${asset:3}	
    case $shortName in
        sqldatabase) deploymentName="CargoTracking.SQLDatabase-$environment"
                        ;;
        sqlserver) deploymentName="CargoTracking.SQLServer-$environment"
                        ;;
        container-registry) deploymentName="CargoTracking.ContainerRegistry-$environment"
                        ;;
        keyvault) deploymentName="CargoTracking.KeyVault-$environment"
                        ;;
        appservice) deploymentName="CargoTracking.AppService-$environment"
    esac
	az group deployment show -n $deploymentName --resource-group $resourceGroup
	exit $?
fi

if [ -z "$tag" ]; then
	tag=`git branch | grep \* | cut -d ' ' -f2`
	tag=`echo $tag | tr / -`
	if [ "$tag" == "master" ]; then
	    tag=latest
	fi

	if [ "$tag" == "feature-access-control" ]; then
		tag=test
	fi
fi


if [ "$op" == "image-build" ]; then
	cd $srcDir/../..
    docker build --build-arg JAR_FILE="target/cargo-tracking-0.0.1-SNAPSHOT.jar" -f Dockerfile -t cargo-tracking:$tag .	
	exit $?
fi

if [ "$op" == "image-deploy-ms" ]; then
        if [ -z "$servicePrincipal" ]; then
                echo "To deploy an image in environment \"$environment\" as service principal must be defined. Please update the manage.sh script"
                echo "Note that the service principal password is expected to be an environment variable following the pattern AZ_PASS_<environment>"
                echo "For manual deployments the password must be stored in ~/.azure_pass with file mode 600. (Note: NEVER checked in to source control!)"
                echo "If the file does not exist an environment variable must have been set, for example by the bitbucket build pipeline"
                exit 1
        fi

        cd $srcDir/../..
        registryServer=`az group deployment show -n CargoTracking.ContainerRegistry-$environment --resource-group $resourceGroup --query properties.outputs.registryServer.value`
        registryServer=`sed -e  's/^"//' -e 's/"$//' <<<$registryServer`
	docker tag $tag $registryServer/$tag

        echo $azurePassword | docker login $registryServer -u $servicePrincipalId --password-stdin && \
        docker push $registryServer/$tag
        exit $?
fi


if [ "$op" == "image-deploy" ]; then
	if [ -z "$servicePrincipal" ]; then
		echo "To deploy an image in environment \"$environment\" as service principal must be defined. Please update the manage.sh script"
		echo "Note that the service principal password is expected to be an environment variable following the pattern AZ_PASS_<environment>"
		echo "For manual deployments the password must be stored in ~/.azure_pass with file mode 600. (Note: NEVER checked in to source control!)"
		echo "If the file does not exist an environment variable must have been set, for example by the bitbucket build pipeline"
		exit 1
	fi

	cd $srcDir/../..
	registryServer=`az group deployment show -n CargoTracking.ContainerRegistry-$environment --resource-group $resourceGroup --query properties.outputs.registryServer.value`
	registryServer=`sed -e  's/^"//' -e 's/"$//' <<<$registryServer`
	
	echo $azurePassword | docker login $registryServer -u $servicePrincipalId --password-stdin && \
	docker tag cargo-tracking:$tag $registryServer/cargo-tracking:$tag && \
	docker push $registryServer/cargo-tracking:$tag
	exit $?
fi

if [ "$op" == "image-pull" ]; then
	if [ -z "$servicePrincipal" ]; then
         echo "To deploy an image in environment \"$environment\" as service principal must be defined. Please update the manage.sh script"
         echo "Note that the service principal password is expected to be an environment variable following the pattern AZ_PASS_<environment>"
         echo "For manual deployments the password must be stored in ~/.azure_pass with file mode 600. (Note: NEVER checked in to source control!)"
         echo "If the file does not exist an environment variable must have been set, for example by the bitbucket build pipeline"
         exit 1
    fi

    cd $srcDir/../..
    registryServer=`az group deployment show -n CargoTracking.ContainerRegistry-$environment --resource-group $resourceGroup --query properties.outputs.registryServer.value`
    registryServer=`sed -e  's/^"//' -e 's/"$//' <<<$registryServer`

    echo $azurePassword | docker login $registryServer -u $servicePrincipalId --password-stdin
    docker tag cargo-tracking $registryServer/cargo-tracking:$tag
    docker pull $registryServer/cargo-tracking:$tag
fi

