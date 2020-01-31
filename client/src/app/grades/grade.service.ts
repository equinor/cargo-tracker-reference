import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grade } from '../shared/models/grade';
import { BASE_URL } from '../tokens';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  private baseUrl: string;

  constructor(private http: HttpClient, @Inject(BASE_URL) baseUrl: string) {
    this.baseUrl = `${baseUrl}/config/grade`;
  }

  public verify(grade: Grade) {
    return this.http.patch(`${this.baseUrl}/${grade.id}/verify`, grade);
  }

  public count(grade: Grade) {
    return this.http.get(`/api/ct/cargo/grade/${grade.id}/count`, { responseType: 'text' })
      .pipe(
        map(res => Number(res))
      );
  }


  public merge(from: Grade, into: Grade) {
    return this.http.put<Grade>(`${this.baseUrl}/${from.id}/replace`, into);
  }

  public save(grade: Grade) {
    if ( grade.id ) {
      return this.update(grade);
    }
    return this.add(grade);
  }

  private add(grade: Grade) {
    return this.http.post(`${this.baseUrl}`, grade);
  }

  private update(grade: Grade) {
    return this.http.put(`${this.baseUrl}/${grade.id}`, grade);
  }

  public cancel(grade: Grade) {
    return this.http.delete(`${this.baseUrl}/${grade.id}`);
  }

  public upload(file: File) {
    const formData = new FormData();
    formData.set('gradesheet', file);
    return this.http.post<void>(`${this.baseUrl}/upload`, formData);
  }
}
