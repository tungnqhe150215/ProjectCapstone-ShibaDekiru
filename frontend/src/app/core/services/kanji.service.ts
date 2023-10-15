import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Kanji } from 'src/app/core/models/kanji';

@Injectable({
  providedIn: 'root',
})
export class KanjiService {
  private baseURL = 'http://localhost:8080/api/admin/kanji';
  kanji: Kanji[] = [];
  constructor(private httpClient: HttpClient) {}

  getKanjiList(): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`);
  }

  createKanji(data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}`, data);
  }

  updateKanji(id: number, data: any): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`, data);
  }

  deleteKanji(id: number): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/${id}`);
  }
  // getLessonByID(id:number):Observable<any>{
  //   return this.httpClient.get<any>(`${this.baseURL}/${id}`);
  // }
}
