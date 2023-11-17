import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ClassWork} from "../../core/models/class-work";
import {Exercise} from "../../core/models/exercise";
import {WritingExercise} from "../../core/models/writing-exercise";
import {WritingExerciseAnswer} from "../../core/models/writing-exercise-answer";
import {StudentClassWork} from "../../core/models/student-class-work";

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

  createWritingExerciseAnswer(writingExerciseAnswer: WritingExerciseAnswer): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/exercise/answer`, writingExerciseAnswer);
  }

  getExerciseByClasswork(id: number): Observable<Exercise[]> {
    return this.httpClient.get<Exercise[]>(`${this.baseUrl}/classwork/${id}/exercise`);
  }

  getWritingExerciseByExercise(id: number): Observable<WritingExercise[]> {
    return this.httpClient.get<WritingExercise[]>(`${this.baseUrl}/exercise/${id}/question`);
  }

  getWritingExerciseByCLasswork(id:number): Observable<WritingExercise[]> {
    return this.httpClient.get<WritingExercise[]>(`${this.baseUrl}/classwork/${id}/question`);
  }

  getStudentClassworkByCLassworkAndStudent(studentId:number,classworkId:number): Observable<StudentClassWork> {
    const params = new HttpParams().set('studentId',studentId).set('classworkId',classworkId)
    return this.httpClient.get<StudentClassWork>(`${this.baseUrl}/classwork/result`,{params:params});
  }

  getWritingExerciseAnswerByClassworkAndStudent(studentId:number,classworkId:number): Observable<WritingExerciseAnswer[]> {
    const params = new HttpParams().set('studentId',studentId).set('classworkId',classworkId)
    return this.httpClient.get<WritingExerciseAnswer[]>(`${this.baseUrl}/classwork/answer`,{params:params});
  }

  updateTest(id: number, test: Test): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/test/${id}`, test);
  }

  createStudentClasswork(studentClasswork: StudentClassWork): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/exercise/result`, studentClasswork);
  }

  deleteTest(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/test/${id}`);
  }
}
