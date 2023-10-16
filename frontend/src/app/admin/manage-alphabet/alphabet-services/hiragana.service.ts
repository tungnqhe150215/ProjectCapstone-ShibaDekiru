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
}
