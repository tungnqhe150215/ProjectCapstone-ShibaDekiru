import { Injectable } from '@angular/core';
import {Class} from "../../core/models/class";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageClassService {

  private baseUrl="http://localhost:8080/api/admin";

  classs: Class[] = [];

  constructor(private httpClient: HttpClient) { }

  getClassByLesson(id:number): Observable<Class[]>{
    return this.httpClient.get<Class[]>(`${this.baseUrl}/class`);
  }

  createClass( id:number,aClass: Class): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/class`,aClass);
  }

  getClassById(id:number): Observable<Class>{
    return this.httpClient.get<Class>(`${this.baseUrl}/class/${id}`);
  }

  updateClass(id:number,aClass:Class): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/class/${id}`,aClass);
  }

  deleteClass(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/class/${id}`);
  }
}
