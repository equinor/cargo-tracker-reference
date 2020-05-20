import { IConfiguration, IConfig } from '@microsoft/applicationinsights-web';

export const config: IConfiguration & IConfig = {
  instrumentationKey: "${APPINSIGHTS_INSTRUMENTATIONKEY}",
  enableAutoRouteTracking: true 
}