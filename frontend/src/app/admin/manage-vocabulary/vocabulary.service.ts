import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vocabulary } from 'src/app/core/models/vocabulary';

@Injectable({
  providedIn: 'root'
})
export class VocabularyService {
  private baseURL = 'http://localhost:8080/api/admin/vocabulary';
  'vocabulary': Vocabulary[];
  constructor(private httpClient: HttpClient) {}
  getVocabulariesList(): Observable<Vocabulary[]> {
    return this.httpClient.get<Vocabulary[]>(`${this.baseURL}`);
  }

  createVocabulary(vocabulary: Vocabulary): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, vocabulary);
  }
  getVocabularyByID(id: number): Observable<Vocabulary> {
    return this.httpClient.get<Vocabulary>(`${this.baseURL}/${id}`);
  }
  updateVocabulary(id: number, vocabulary: Vocabulary): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, vocabulary);
  }
  deleteVocabulary(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
