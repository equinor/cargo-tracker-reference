import { map } from 'rxjs/operators';
import { from, Observable } from 'rxjs';
import { NgForageCache, CachedItem } from 'ngforage';

export const storeInDb = <T = any>(key: string, ngf: NgForageCache) => map<T, T>((value: T) => {
  ngf.setCached(key, value);
  return value;
});

export const checkDb = <T = any>(key: string, ngf: NgForageCache, apiCall: Observable<T> ): Observable<T> => {
  return from(ngf.getCached<T>(key)
    .then((item: CachedItem<T>) => {
      return apiCall.toPromise();
      if (!item || !item.hasData || item.expired){
        return apiCall.toPromise();
      }
      return item.data;
    }));
};

export const deleteCache = (key: string , ngf: NgForageCache) => (() => {
  ngf.removeCached(key);
});
