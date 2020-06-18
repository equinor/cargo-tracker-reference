import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BASE_URL } from './tokens';
import { Grade } from './shared/models/grade';
import { Country, Terminal } from './shared/models/location';
import { Region } from './shared/models/region';
import { Company } from './shared/models/company';
import { catchError } from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { ClientAuthError } from 'msal';


const staticErrorHandler = <T>() => catchError<T[], Observable<T[]>>((err: HttpErrorResponse | ClientAuthError) => {
  if ( err instanceof ClientAuthError ) {
    return of(null) as Observable<any[]>;
  }
  return throwError(err);
});

@Injectable({
  providedIn: 'root'
})
export class StaticService {
  private baseUrl: string;

  constructor(private http: HttpClient, @Inject(BASE_URL) baseUrl: string) {
    this.baseUrl = `${baseUrl}/config`;
  }

  grades() {
    return this.http.get<Grade[]>(`${this.baseUrl}/grade`)
      .pipe(staticErrorHandler<Grade>());
  }

  countries() {
    return this.http.get<Country[]>(`${this.baseUrl}/country`)
      .pipe(staticErrorHandler<Country>());
  }

  regions() {
    return this.http.get<Region[]>(`${this.baseUrl}/region`)
      .pipe(staticErrorHandler<Region>());
  }

  terminals() {
    return this.http.get<Terminal[]>(`${this.baseUrl}/terminal`)
      .pipe(staticErrorHandler<Terminal>());
  }

  companies() {
    return this.http.get<Company[]>(`${this.baseUrl}/company`)
      .pipe(staticErrorHandler<Company>());
  }

  environment() {
    return this.http.get<any>(`assets/environment.json`)
      .pipe(staticErrorHandler<Company>());
  }
}
