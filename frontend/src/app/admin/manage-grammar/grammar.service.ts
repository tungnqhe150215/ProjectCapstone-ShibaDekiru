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

  getGrammarList(): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`);
  }

  createGrammar(data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`, data);
  }

  updateGrammar(id: number, data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`, data);
  }

  deleteGrammar(id: number): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`);
  }
  // getLessonByID(id:number):Observable<any>{
  //   return this.httpClient.get<any>(`${this.baseURL}/${id}`);
  // }
}
