import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vocabulary } from 'src/app/core/models/vocabulary';

@Injectable({
  providedIn: 'root',
})
export class VocabularyService {
  private baseURL = 'http://localhost:8080/api/admin/vocabulary';
  vocabulary: Vocabulary[] = [];
  constructor(private httpClient: HttpClient) {}

  getVocabularyList(): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`);
  }

  createVocabulary(data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`, data);
  }

  updateVocabulary(id: number, data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`, data);
  }

  deleteVocabulary(id: number): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`);
  }
  // getLessonByID(id:number):Observable<any>{
  //   return this.httpClient.get<any>(`${this.baseURL}/${id}`);
  // }
}
