import { NgForageOptions, Driver } from 'ngforage';


export const ngfRootOptions:NgForageOptions = {
  name: 'ct-storage',  
  driver: [
    Driver.INDEXED_DB,
    Driver.WEB_SQL,
    Driver.LOCAL_STORAGE
  ],
  cacheTime: 1800000 // milliseconds
};
