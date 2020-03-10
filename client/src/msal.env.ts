import { MsalConfig } from '@azure/msal-angular';

const authority = 'https://login.microsoftonline.com/3aa4a235-b6e2-48d5-9195-7fcf05b459b0';
const redirectUri = '${REDIRECT_URI}';

export const config: MsalConfig = {
  authority,
  redirectUri,
  navigateToLoginRequestUrl: true,
  clientID: '${CLIENT_ID}'
};
