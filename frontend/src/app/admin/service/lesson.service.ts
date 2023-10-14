
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Lesson } from 'src/app/core/models/lesson';

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  private baseURL = "http://localhost:8080/api/admin";
  lesson: Lesson[] =[];
  constructor(private httpClient: HttpClient,) { }

  getLessonList(): Observable<any>{
    return this.httpClient.get(`${this.baseURL}`);
  }

}
