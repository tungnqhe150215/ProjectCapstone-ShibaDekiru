import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Hiragana } from 'src/app/core/models/hiragana';
import { Katakana } from 'src/app/core/models/katakana';

@Injectable({
  providedIn: 'root'
})
export class AlphabetService {

  private baseURL= "http://localhost:8080/api/alphabet";
  constructor(private httpClient: HttpClient) { }

  hiragana: Hiragana[] = [];
  katakana: Katakana[] = [];

  getAllHiragana(): Observable<Hiragana[]>{
    return this.httpClient.get<Hiragana[]>(`${this.baseURL}/hiragana`);

  }

  getHiraByID(id:number): Observable<Hiragana>{
    return this.httpClient.get<Hiragana>(`${this.baseURL}/hiragana/${id}`);
  }

  getKataByID(id:number): Observable<Katakana>{
    return this.httpClient.get<Katakana>(`${this.baseURL}/katakana/${id}`);
  }

  getAllKatakana(): Observable<Katakana[]>{
    return this.httpClient.get<Katakana[]>(`${this.baseURL}/katakana`);
  }
}
