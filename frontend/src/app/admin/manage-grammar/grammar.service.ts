import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Grammar } from 'src/app/core/models/grammar';

@Injectable({
  providedIn: 'root',
})
export class GrammarService {
  private baseURL = 'http://localhost:8080/api/admin/grammar';
  grammar: Grammar[] = [];
  constructor(private httpClient: HttpClient) {}
  getGrammarsList(): Observable<Grammar[]> {
    return this.httpClient.get<Grammar[]>(`${this.baseURL}`);
  }

  createGrammar(grammar: Grammar): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, grammar);
  }
  getGrammarByID(id: number): Observable<Grammar> {
    return this.httpClient.get<Grammar>(`${this.baseURL}/${id}`);
  }
  updateGrammar(id: number, grammar: Grammar): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, grammar);
  }
  deleteGrammar(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
