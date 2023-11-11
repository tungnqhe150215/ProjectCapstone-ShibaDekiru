import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Class} from "../../core/models/class";

@Injectable({
  providedIn: 'root'
})
export class StudentClassService {

  private baseUrl = "http://localhost:8080/api/student";

  tests: Test[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getClassById(id: number): Observable<Class> {
    return this.httpClient.get<Class>(`${this.baseUrl}/class/${id}`);
  }
}
