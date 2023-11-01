import { Injectable } from '@angular/core';
import {QuestionBank} from "../../core/models/question-bank";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LectureManageQuestionBankService {

  private baseUrl="http://localhost:8080/api/lecture/test";

  questionBanks: QuestionBank[] = [];

  constructor(private httpClient: HttpClient) { }

  getquestionBankByTest(id:number): Observable<QuestionBank[]>{
    return this.httpClient.get<QuestionBank[]>(`${this.baseUrl}/${id}/question`);
  }

  createQuestionBank(id:number,questionBank:QuestionBank): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/question`,questionBank);
  }

  getQuestionBankById(id:number): Observable<QuestionBank>{
    return this.httpClient.get<QuestionBank>(`${this.baseUrl}/question/${id}`);
  }

  updateQuestionBank(id:number,questionBank:QuestionBank): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/question/${id}`,questionBank);
  }

  deleteQuestionBank(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/question/${id}`);
  }
}
