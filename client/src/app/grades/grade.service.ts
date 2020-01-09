import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grade } from '../shared/models/grade';
import { BASE_URL } from '../tokens';

@Injectable({
  providedIn: 'root'
})
export class GradeService {

  constructor(private http: HttpClient, @Inject(BASE_URL) private baseUrl: string) {
  }

  public verify(grade: Grade) {
    return this.http.patch(`${this.baseUrl}/config/grade/${grade.id}/verify`, grade);
  }

  public save(grade: Grade) {
    if ( grade.id ) {
      return this.update(grade);
    }
    return this.add(grade);
  }

  private add(grade: Grade) {
    return this.http.post(`${this.baseUrl}/config/grade`, grade);
  }

  private update(grade: Grade) {
    return this.http.put(`${this.baseUrl}/config/grade/${grade.id}`, grade);
  }

  public cancel(grade: Grade) {
    return this.http.delete(`${this.baseUrl}/config/grade/${grade.id}`);
  }
}
