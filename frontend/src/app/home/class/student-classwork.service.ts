import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ClassWork} from "../../core/models/class-work";
import {Exercise} from "../../core/models/exercise";
import {WritingExercise} from "../../core/models/writing-exercise";

@Injectable({
  providedIn: 'root'
})
export class StudentClassworkService {

  private baseUrl = "http://localhost:8080/api/student";

  tests: Test[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getClassWorkByClass(id: number): Observable<ClassWork[]> {
    return this.httpClient.get<ClassWork[]>(`${this.baseUrl}/class/${id}/classwork`);
  }

  getClassWorkById(id: number): Observable<ClassWork> {
    return this.httpClient.get<ClassWork>(`${this.baseUrl}/classwork/${id}`);
  }

  createTest(id: number, test: Test): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/test`, test);
  }

  getExerciseByClasswork(id: number): Observable<Exercise[]> {
    return this.httpClient.get<Exercise[]>(`${this.baseUrl}/classwork/${id}/exercise`);
  }

  getWritingExerciseByExercise(id: number): Observable<WritingExercise[]> {
    return this.httpClient.get<WritingExercise[]>(`${this.baseUrl}/exercise/${id}/question`);
  }

  updateTest(id: number, test: Test): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/test/${id}`, test);
  }

  deleteTest(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/test/${id}`);
  }
}
