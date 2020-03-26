import { Configuration } from 'msal';

const authority = 'https://login.microsoftonline.com/3aa4a235-b6e2-48d5-9195-7fcf05b459b0';
const redirectUri = `http://localhost:4200/callback`;

export const config: Configuration = {
  auth: {
    authority,
    redirectUri,
    navigateToLoginRequestUrl: false,
    clientId: 'c176aebb-5104-42ba-a0dd-a8be3d7e0907'
  }
};
