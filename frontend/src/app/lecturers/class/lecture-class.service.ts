import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Class} from "../../core/models/class";

@Injectable({
  providedIn: 'root'
})
export class LectureClassService {

  private baseUrl="http://localhost:8080/api/lecture";

  class: Class[] = [];

  constructor(private httpClient: HttpClient) { }

  getClassByLecture(id:number): Observable<Class[]>{
    return this.httpClient.get<Class[]>(`${this.baseUrl}/${id}/class`);
  }

  createClass(id:number,class1:Class): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/class`,class1);
  }

  getClassById(id:number): Observable<Class>{
    return this.httpClient.get<Class>(`${this.baseUrl}/class/${id}`);
  }

  updateClass(id:number,class1:Class): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/class/${id}`,class1);
  }

  deleteClass(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/class/${id}`);
  }
}
