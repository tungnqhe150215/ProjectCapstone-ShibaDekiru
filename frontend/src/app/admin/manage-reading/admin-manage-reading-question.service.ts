import { Injectable } from '@angular/core';
import {ReadingQuestion} from "../../core/models/reading-question";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageReadingQuestionService {

  private baseUrl="http://localhost:8080/api/admin/lesson/reading";

  readingQuestion: ReadingQuestion[] = [];

  constructor(private httpClient: HttpClient) { }

  getreadingQuestionByReading(id:number): Observable<ReadingQuestion[]>{
    return this.httpClient.get<ReadingQuestion[]>(`${this.baseUrl}/${id}/reading-question`);
  }

  createReadingQuestion(id:number,readingQuestion:ReadingQuestion): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/reading-question`,readingQuestion);
  }

  getReadingQuestionById(id:number): Observable<ReadingQuestion>{
    return this.httpClient.get<ReadingQuestion>(`${this.baseUrl}/reading-question/${id}`);
  }

  updateReadingQuestion(id:number,readingQuestion:ReadingQuestion): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/reading-question/${id}`,readingQuestion);
  }

  deleteReadingQuestion(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/reading-question/${id}`);
  }
}
