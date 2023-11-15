import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TestSection} from "../../core/models/test-section";
import {QuestionBank} from "../../core/models/question-bank";

@Injectable({
  providedIn: 'root'
})
export class StudentTestService {


  private baseUrl = "http://localhost:8080/api/student";

  tests: Test[] = [];

  test: Test = new Test();

  constructor(private httpClient: HttpClient) {
  }

  getTestById(id: number): Observable<Test> {
    return this.httpClient.get<Test>(`${this.baseUrl}/test/${id}`);
  }

  getTestSectionByTest(id: number): Observable<TestSection[]> {
    return this.httpClient.get<TestSection[]>(`${this.baseUrl}/test/${id}/section`);
  }

  getTestSectionById(id: number): Observable<TestSection> {
    return this.httpClient.get<TestSection>(`${this.baseUrl}/test/section/${id}`);
  }

  getQuestionById(id: number): Observable<QuestionBank> {
    return this.httpClient.get<QuestionBank>(`${this.baseUrl}/test/${id}`);
  }

  getQuestionBySectionId(id:number): Observable<QuestionBank[]>{
    return this.httpClient.get<QuestionBank[]>(`${this.baseUrl}/test/section/${id}/question`);
  }

  getQuestionByTest(id:number): Observable<QuestionBank[]>{
    return this.httpClient.get<QuestionBank[]>(`${this.baseUrl}/test/${id}/question`);
  }
}
