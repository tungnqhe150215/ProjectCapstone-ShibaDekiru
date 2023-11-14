import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {QuestionBank} from "../../core/models/question-bank";
import {TestSection} from "../../core/models/test-section";
import {TestAssign} from "../../core/models/test-assign";

@Injectable({
  providedIn: 'root'
})
export class ClassTestService {

  private baseUrl = "http://localhost:8080/api/student";

  tests: Test[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getTestByClass(id: number): Observable<TestAssign[]> {
    return this.httpClient.get<TestAssign[]>(`${this.baseUrl}/class/${id}/test`);
  }

  getTestById(id: number): Observable<Test> {
    return this.httpClient.get<Test>(`${this.baseUrl}/test/${id}`);
  }

  getTestSectionByTest(id: number): Observable<TestSection[]> {
    return this.httpClient.get<TestSection[]>(`${this.baseUrl}/test/${id}`);
  }
  getQuestionByTestSection(id:number): Observable<QuestionBank[]>{
    return  this.httpClient.get<QuestionBank[]>(`${this.baseUrl}/test/section/${id}`);
  }
}
