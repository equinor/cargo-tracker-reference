import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BASE_URL } from './tokens';
import { Grade } from './shared/models/grade';
import { Country, Terminal } from './shared/models/location';
import { Region } from './shared/models/region';
import { Company } from './shared/models/company';
import { catchError } from 'rxjs/operators';
import { of, throwError } from 'rxjs';

const staticErrorHandler = catchError((err: HttpErrorResponse) => {
  if (err.status === 401 || err.status === 403) {
    return of([])
  }
  return throwError(err)
})

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
    .pipe(staticErrorHandler)
  }

  countries() {
    return this.http.get<Country[]>(`${this.baseUrl}/country`)
    .pipe(staticErrorHandler)
  }

  regions() {
    return this.http.get<Region[]>(`${this.baseUrl}/region`)
    .pipe(staticErrorHandler)
  }

  terminals() {
    return this.http.get<Terminal[]>(`${this.baseUrl}/terminal`)
    .pipe(staticErrorHandler)
  }

  companies() {
    return this.http.get<Company[]>(`${this.baseUrl}/company`)
    .pipe(staticErrorHandler)
  }
}
