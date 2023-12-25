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

  getTestResultByStudentAndTest(studentId: number,testId: number): Observable<TestResult[]> {
    const params = new HttpParams().set('studentId', studentId).set('testId',testId)
    return this.httpClient.get<TestResult[]>(`${this.baseUrl}/result`,{params: params});
  }

  getTestResultByStudentAndTestAssign(studentId: number,testAssignId: number): Observable<TestResult[]> {
    const params = new HttpParams().set('studentId', studentId).set('testAssignId',testAssignId);
    return this.httpClient.get<TestResult[]>(`${this.baseUrl}/result`,{params: params});
  }

  updateTestSection(id: number, testSection: TestSection): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/section/${id}`, testSection);
  }

  deleteTestSection(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/section/${id}`);
  }

  deleteTestResult(studentId: number,testAssignId: number): Observable<Object>{
    const params = new HttpParams().set('studentId', studentId).set('testAssignId',testAssignId);
    return this.httpClient.delete(`${this.baseUrl}/result`,{params: params});
  }
}
