import { Injectable } from '@angular/core';
import {Writing} from "../../core/models/writing";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {WritingQuestion} from "../../core/models/writing-question";

@Injectable({
  providedIn: 'root'
})
export class AdminManageWritingQuestionService {


  private baseUrl="http://localhost:8080/api/admin/lesson/writing";

  writingQuestion: WritingQuestion[] = [];

  constructor(private httpClient: HttpClient) { }

  getwritingQuestionByLesson(id:number): Observable<WritingQuestion[]>{
    return this.httpClient.get<WritingQuestion[]>(`${this.baseUrl}/${id}/writing-question`);
  }

  createWritingQuestion(writingQuestion:WritingQuestion): Observable<Object>{
    return this.httpClient.post(this.baseUrl,writingQuestion);
  }

  getWritingQuestionById(id:number): Observable<WritingQuestion>{
    return this.httpClient.get<WritingQuestion>(`${this.baseUrl}/writing-question/${id}`);
  }

  updateWritingQuestion(id:number,writingQuestion:WritingQuestion): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/writing-question/${id}`,writingQuestion);
  }

  deleteWritingQuestion(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/writing-question/${id}`);
  }
}
