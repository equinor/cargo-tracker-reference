import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Company } from '../shared/models/company';
import { BASE_URL } from '../tokens';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private baseUrl: string;

  constructor(private http: HttpClient, @Inject(BASE_URL) baseUrl: string) {
    this.baseUrl = `${baseUrl}/config/company`;
  }

  save(company: Company) {
    if ( company.id ) {
      return this.update(company);
    }
    return this.add(company);
  }

  verify(company: Company) {
    return this.http.patch(`${this.baseUrl}/${company.id}/verify`, company);
  }

  count(company: Company) {
    return this.http.get(`/api/ct/cargo/company/${company.id}/count`, { responseType: 'text' })
      .pipe(
        map(res => Number(res))
      );
  }

  cancel(company: Company) {
    return this.http.delete(`${this.baseUrl}/${company.id}`);
  }

  private add(company: Company) {
    return this.http.post<Company>(`${this.baseUrl}`, company);
  }

  private update(company: Company) {
    return this.http.put<Company>(`${this.baseUrl}/${company.id}`, company);
  }
}
