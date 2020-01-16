import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BASE_URL } from '../tokens';
import { Terminal } from '../shared/models/location';

@Injectable()
export class TerminalsService {
  private baseUrl: string;

  constructor(private http: HttpClient, @Inject(BASE_URL) baseUrl: string) {
    this.baseUrl = `${baseUrl}/config/terminal`;
  }

  save(terminal: Terminal) {
    return this.http.put(`${this.baseUrl}/${terminal.id}`, terminal);
  }
}
