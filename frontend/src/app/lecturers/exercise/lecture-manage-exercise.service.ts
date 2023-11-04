import { Injectable } from '@angular/core';
import {Exercise} from "../../core/models/exercise";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LectureManageExerciseService {
  private baseUrl="http://localhost:8080/api/lecture/class/classwork";

  exercises: Exercise[] = [];

  constructor(private httpClient: HttpClient) { }

  getExerciseByClasswork(id:number): Observable<Exercise[]>{
    return this.httpClient.get<Exercise[]>(`${this.baseUrl}/${id}/exercise`);
  }

  createExercise( id:number,exercise: Exercise): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/exercise`,exercise);
  }

  getExerciseById(id:number): Observable<Exercise>{
    return this.httpClient.get<Exercise>(`${this.baseUrl}/exercise/${id}`);
  }

  updateExercise(id:number,exercise:Exercise): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/exercise/${id}`,exercise);
  }

  deleteExercise(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/exercise/${id}`);
  }
}
