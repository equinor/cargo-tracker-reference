import { detect } from 'detect-browser';
import { BrowserCacheLocation, IPublicClientApplication, PublicClientApplication } from '@azure/msal-browser';

const browser = detect().name;

const storeAuthStateInCookie = browser === 'edge' || browser === 'ie';
let cacheLocation: BrowserCacheLocation = BrowserCacheLocation.SessionStorage;
if ( storeAuthStateInCookie ) {
  cacheLocation = BrowserCacheLocation.LocalStorage;
}

const authority = 'https://login.microsoftonline.com/3aa4a235-b6e2-48d5-9195-7fcf05b459b0';
const redirectUri = '${REDIRECT_URI}';

export const msalConfig: IPublicClientApplication = new PublicClientApplication({
  auth: {
    authority,
    redirectUri,
    navigateToLoginRequestUrl: true,
    clientId: '${CLIENT_ID}'
  },
  cache: {
    cacheLocation,
    storeAuthStateInCookie
  }
});
