import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClassWork } from 'src/app/core/models/class-work';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClassworkService {

  constructor(private httpClient: HttpClient) { }

  private baseURL ="http://localhost:8080/api/lecture/class";

  classWork: ClassWork[] = [];

  getAllClassWorkInClass(id:number): Observable<ClassWork[]> {
    return this.httpClient.get<ClassWork[]>(`${this.baseURL}/${id}/class-work`);
  }

  getClassWorkByID(id: number): Observable<ClassWork>{
    return this.httpClient.get<ClassWork>(`${this.baseURL}/class-work/${id}`);
  }
  createClassWork(id: number, classWork:ClassWork): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/${id}/class-work`, classWork);
  }

  updateClassWork(id: number, classWork:ClassWork): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/class-work/${id}`,classWork);
  }

  deleteClassWork(id: number): Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/class-work/${id}`);
  }

  disableClassWork(id:number){
    return this.httpClient.put<void>(`${this.baseURL}/class-work/update-is-locked/${id}`,{});
  }
}
