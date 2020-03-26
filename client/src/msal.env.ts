import { Configuration, CacheLocation } from 'msal';
import { detect } from 'detect-browser';

const browser = detect().name;

let storeAuthStateInCookie = browser === 'edge' || browser === 'ie';
let cacheLocation: CacheLocation = 'sessionStorage';
if (storeAuthStateInCookie) {
  cacheLocation = 'localStorage';
}

const authority = 'https://login.microsoftonline.com/3aa4a235-b6e2-48d5-9195-7fcf05b459b0';
const redirectUri = '${REDIRECT_URI}';

export const config: Configuration = {
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
};
