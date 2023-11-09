import { Injectable } from '@angular/core';
import {TestAssign} from "../../core/models/test-assign";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {TestResult} from "../../core/models/test-result";

@Injectable({
  providedIn: 'root'
})
export class LectureManageTestResultService {

  private baseUrl = "http://localhost:8080/api/lecture/test";

  testAssign: TestAssign[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getTestResultByTest(id: number): Observable<TestResult[]> {
    return this.httpClient.get<TestResult[]>(`${this.baseUrl}/${id}/test-result`);
  }

}
