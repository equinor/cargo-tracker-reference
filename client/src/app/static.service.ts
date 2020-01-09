import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BASE_URL } from './tokens';
import { Grade } from './shared/models/grade';
import { Country, Terminal } from './shared/models/location';
import { Region } from './shared/models/region';
import { Company } from './shared/models/company';

@Injectable({
  providedIn: 'root'
})
export class StaticService {

  constructor(private http: HttpClient, @Inject(BASE_URL) private baseUrl: string) {
  }

  grades() {
    return this.http.get<Grade[]>(`${this.baseUrl}/config/grade`);
  }

  countries() {
    return this.http.get<Country[]>(`${this.baseUrl}/config/country`);
  }

  regions() {
    return this.http.get<Region[]>(`${this.baseUrl}/grade`);
  }

  terminals() {
    return this.http.get<Terminal[]>(`${this.baseUrl}/grade`);
  }

  companies() {
    return this.http.get<Company[]>(`${this.baseUrl}/company`);
  }
}
