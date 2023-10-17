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
  getKanjisList(): Observable<Kanji[]> {
    return this.httpClient.get<Kanji[]>(`${this.baseURL}`);
  }

  createKanji(kanji: Kanji): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, kanji);
  }
  getKanjiByID(id: number): Observable<Kanji> {
    return this.httpClient.get<Kanji>(`${this.baseURL}/${id}`);
  }
  updateKanji(id: number, kanji: Kanji): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, kanji);
  }
  deleteKanji(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
