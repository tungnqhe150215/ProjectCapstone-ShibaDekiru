import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Grammar } from 'src/app/core/models/grammar';
import { Kanji } from 'src/app/core/models/kanji';
import { Vocabulary } from 'src/app/core/models/vocabulary';

@Injectable({
  providedIn: 'root'
})
export class KnowledgeServiceService {

  kanji: Kanji[] = [];
  grammar: Grammar[] = [];
  vocab: Vocabulary[] = [];

  private baseURL = 'http://localhost:8080/api/knowledge';

  constructor(private httpClient: HttpClient) { }

  getAllKanji(): Observable<Kanji[]>{
    return this.httpClient.get<Kanji[]>(`${this.baseURL}/kanji`);
  }

  getKanjiById(id: number): Observable<Kanji>{
    return this.httpClient.get<Kanji>(`${this.baseURL}/kanji/${id}`);
  }

  getAllVocab() :Observable<Vocabulary[]>{
    return this.httpClient.get<Vocabulary[]>(`${this.baseURL}/vocabulary`);
  }

  getVocabById(id:number) : Observable<Vocabulary>{
    return this.httpClient.get<Vocabulary>(`${this.baseURL}/vocabulary/${id}`);
  }

  getAllGrammar() :Observable<Grammar[]>{
    return this.httpClient.get<Grammar[]>(`${this.baseURL}/grammar`);
  }

  getGrammarById(id: number): Observable<Grammar>{
    return this.httpClient.get<Grammar>(`${this.baseURL}/grammar/${id}`);
  }
}
