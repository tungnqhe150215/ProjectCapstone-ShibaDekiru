import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Katakana } from 'src/app/core/models/katakana';

@Injectable({
  providedIn: 'root'
})
export class KatakanaService {
  private baseURL = 'http://localhost:8080/api/admin/katakana';
  katakana: Katakana[] = [];
  constructor(private httpClient: HttpClient) {}
  getKatakanaList(): Observable<Katakana[]> {
    return this.httpClient.get<Katakana[]>(`${this.baseURL}`);
  }
}
