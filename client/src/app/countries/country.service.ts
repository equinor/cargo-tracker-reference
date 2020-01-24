import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Country } from '../shared/models/location';
import { BASE_URL } from '../tokens';

@Injectable()
export class CountryService {
  constructor(private http: HttpClient, @Inject(BASE_URL) private baseUrl: string) {
  }

  // For country, we can only ever patch the regionId, so no need for add / update
  save(country: Country) {
    return this.http.patch(`${this.baseUrl}/config/country/${country.id}`, country);
  }
}
