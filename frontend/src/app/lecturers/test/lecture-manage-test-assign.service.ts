import {Injectable} from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {TestAssign} from "../../core/models/test-assign";

@Injectable({
  providedIn: 'root'
})
export class LectureManageTestAssignService {

  private baseUrl = "http://localhost:8080/api/lecture/test";

  testAssign: TestAssign[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getTestAssignByTest(id: number): Observable<TestAssign[]> {
    return this.httpClient.get<TestAssign[]>(`${this.baseUrl}/${id}/assign`);
  }

  createTestAssign(id: number, testAssign: TestAssign, extendTime: number): Observable<Object> {
    const params = new HttpParams().set('extendTime', extendTime);
    return this.httpClient.post(`${this.baseUrl}/assign`, testAssign, {params: params});
  }

  getTestAsignById(id: number): Observable<TestAssign> {
    return this.httpClient.get<TestAssign>(`${this.baseUrl}/assign/${id}`);
  }

  updateTestAssign(id: number, extendTime: number): Observable<Object> {
    const params = new HttpParams()
      .set('id', id)
      .set('extendTime', extendTime);
    return this.httpClient.put(`${this.baseUrl}/assign`, null, {params: params});
  }

  deleteTestAssign(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/assign/${id}`);
  }
}
