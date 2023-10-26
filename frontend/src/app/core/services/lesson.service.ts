
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Lesson } from 'src/app/core/models/lesson';

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  private baseURL = "http://localhost:8080/api/admin/lesson";
  lesson: Lesson[] =[];
  constructor(private httpClient: HttpClient,) { }

  getLessonList(): Observable<Lesson[]>{
    return this.httpClient.get<Lesson[]>(`${this.baseURL}`);
  }

  createLesson(lesson: Lesson): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`,lesson);
  }

  getLessonByID(id:number):Observable<Lesson>{
    return this.httpClient.get<Lesson>(`${this.baseURL}/${id}`);
  }
  updateLesson(id: number, lesson: Lesson): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}`,lesson);
  }

  deleteLesson(id: number): Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }

  // getStatus(): Observable<{status: boolean}>{
  //   const getURL =  '${this.baseURL}/${id}';
  //   return this.httpClient.get<{status: boolean}>(getURL);
  // }
  // getLessonByID(id:number):Observable<any>{
  //   return this.httpClient.get<any>(`${this.baseURL}/${id}`);
  // }
}
