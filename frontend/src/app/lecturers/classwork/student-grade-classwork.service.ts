import { Injectable } from '@angular/core';
import {Test} from "../../core/models/test";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ClassWork} from "../../core/models/class-work";
import {WritingExerciseAnswer} from "../../core/models/writing-exercise-answer";
import {Exercise} from "../../core/models/exercise";
import {WritingExercise} from "../../core/models/writing-exercise";
import {StudentClassWork} from "../../core/models/student-class-work";

@Injectable({
  providedIn: 'root'
})
export class StudentGradeClassworkService {


  private baseUrl = "http://localhost:8080/api/lecture";

  tests: Test[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getStudentClassWorkByClassWork(id: number): Observable<StudentClassWork[]> {
    return this.httpClient.get<StudentClassWork[]>(`${this.baseUrl}/classwork/${id}/submission`);
  }

  getClassWorkById(id: number): Observable<ClassWork> {
    return this.httpClient.get<ClassWork>(`${this.baseUrl}/classwork/${id}`);
  }

  updateWritingExerciseAnswer(writingExerciseAnswer: WritingExerciseAnswer): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/exercise/answer`, writingExerciseAnswer);
  }

  updateStudentClasswork(studentClasswork: StudentClassWork): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/exercise/result`, studentClasswork);
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

  getStudentClassworkByCLassAndStudent(studentId:number,classId:number): Observable<StudentClassWork[]> {
    const params = new HttpParams().set('studentId',studentId).set('classId',classId)
    return this.httpClient.get<StudentClassWork[]>(`${this.baseUrl}/class/classwork/result`,{params:params});
  }

  getWritingExerciseAnswerByClassworkAndStudent(studentId:number,classworkId:number): Observable<WritingExerciseAnswer[]> {
    const params = new HttpParams().set('studentId',studentId).set('classworkId',classworkId)
    return this.httpClient.get<WritingExerciseAnswer[]>(`${this.baseUrl}/classwork/answer`,{params:params});
  }

  getWritingExerciseAnswerByExerciseAndStudent(studentId:number,exerciseId:number): Observable<WritingExerciseAnswer[]> {
    const params = new HttpParams().set('studentId',studentId).set('exerciseId',exerciseId)
    return this.httpClient.get<WritingExerciseAnswer[]>(`${this.baseUrl}/exercise/answer`,{params:params});
  }
}
