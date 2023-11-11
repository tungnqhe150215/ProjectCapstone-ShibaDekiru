import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentClassworkService {

  private baseUrl = "http://localhost:8080/api/lecture";

  tests: Test[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getTestByLecture(id: number): Observable<Test[]> {
    return this.httpClient.get<Test[]>(`${this.baseUrl}/test`);
  }

  createTest(id: number, test: Test): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/test`, test);
  }

  getTestById(id: number): Observable<Test> {
    return this.httpClient.get<Test>(`${this.baseUrl}/test/${id}`);
  }

  updateTest(id: number, test: Test): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/test/${id}`, test);
  }

  deleteTest(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/test/${id}`);
  }
}
