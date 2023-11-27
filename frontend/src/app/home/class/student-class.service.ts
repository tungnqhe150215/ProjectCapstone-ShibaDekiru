import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Class} from "../../core/models/class";
import {ClassStudent} from "../../core/models/class-student";

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

  joinClassByClassCode(studentId: number,code:string): Observable<Object>{
    const param = new HttpParams().set("studentId",studentId)
      .set("code",code);
    return this.httpClient.post(`${this.baseUrl}/class/join`,null,{params: param})
  }

  getClassByStudent(id:number): Observable<ClassStudent[]> {
    const params = new HttpParams().set("studentId",id)
    return this.httpClient.get<ClassStudent[]>(`${this.baseUrl}/class/list`,{params:params});
  }
}
