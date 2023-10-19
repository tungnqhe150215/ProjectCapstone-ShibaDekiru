import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Hiragana } from 'src/app/core/models/hiragana';

@Injectable({
  providedIn: 'root',
})
export class HiraganaService {
  private baseURL = 'http://localhost:8080/api/admin/hiragana';
  hiragana: Hiragana[] = [];
  constructor(private httpClient: HttpClient) {}
  getHiraganaList(): Observable<Hiragana[]> {
    return this.httpClient.get<Hiragana[]>(`${this.baseURL}`);
  }
  createHiragana(hiragana: Hiragana): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, hiragana);
  }
  getHiraganaByID(id: number): Observable<Hiragana> {
    return this.httpClient.get<Hiragana>(`${this.baseURL}/${id}`);
  }
  updateHiragana(id: number, hiragana: Hiragana): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, hiragana);
  }
  deleteHiragana(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
