import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {TestSection} from "../../core/models/test-section";
import {QuestionBank} from "../../core/models/question-bank";
import {TestResult} from "../../core/models/test-result";

@Injectable({
  providedIn: 'root'
})
export class StudentTestResultService {
  private baseUrl = "http://localhost:8080/api/student/test";

  testSections: TestSection[] = [];



  constructor(private httpClient: HttpClient) {
  }

  getTestSectionByTestAndType(id: number, type: string): Observable<TestSection[]> {

    const params = new HttpParams().set('type', type)

    return this.httpClient.get<TestSection[]>(`${this.baseUrl}/${id}/section`, {params: params});
  }

  createTestResult( testResult: TestResult): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/result`, testResult);
  }

  getTestResultById(id: number): Observable<TestSection> {
    return this.httpClient.get<TestSection>(`${this.baseUrl}/section/${id}`);
  }

  updateTestSection(id: number, testSection: TestSection): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/section/${id}`, testSection);
  }

  deleteTestSection(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/section/${id}`);
  }
}
