import { Injectable } from '@angular/core';
import {WritingExercise} from "../../core/models/writing-exercise";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LecturerManageWritingExerciseService {
  private baseUrl="http://localhost:8080/api/lecturer/class/classwork/exercise";

  writingExercises: WritingExercise[] = [];

  constructor(private httpClient: HttpClient) { }

  getWritingExerciseByExercise(id:number): Observable<WritingExercise[]>{
    return this.httpClient.get<WritingExercise[]>(`${this.baseUrl}/${id}/writing-exercise`);
  }

  createWritingExercise( id:number,writingExercise: WritingExercise): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/writing-exercise`,writingExercise);
  }

  getWritingExerciseById(id:number): Observable<WritingExercise>{
    return this.httpClient.get<WritingExercise>(`${this.baseUrl}/writing-exercise/${id}`);
  }

  updateWritingExercise(id:number,writingExercise:WritingExercise): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/writing-exercise/${id}`,writingExercise);
  }

  deleteWritingExercise(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/writing-exercise/${id}`);
  }
}
