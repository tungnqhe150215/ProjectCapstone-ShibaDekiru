import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from 'src/app/core/models/book';
import { Grammar } from 'src/app/core/models/grammar';
import { Kaiwa } from 'src/app/core/models/kaiwa';
import { Kanji } from 'src/app/core/models/kanji';
import { Lesson } from 'src/app/core/models/lesson';
import { Listening } from 'src/app/core/models/listening';
import { ListeningQuestion } from 'src/app/core/models/listening-question';
import { Reading } from 'src/app/core/models/reading';
import { ReadingQuestion } from 'src/app/core/models/reading-question';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { Writing } from 'src/app/core/models/writing';
import { WritingQuestion } from 'src/app/core/models/writing-question';

@Injectable({
  providedIn: 'root'
})
export class StudentLessonService {

   //set storage ID Lesson 
  private idLesson !:number;

  setLessonID(id: number){
    this.idLesson = id;
  }

  getLessonID(){
    return this.idLesson;
  }
  //set storage ID Lesson 
  
  //set storage ID book 
  private bookId!: number;

  setBookId(id: number) {
    this.bookId = id;
  }

  getBookId() {
    return this.bookId;
  }
  //set storage ID book 


  private baseURL ="http://localhost:8080/api";
  lessonID: Lesson = new Lesson;
  book: Book[]= [];
  lesson: Lesson[] =[]; // 1
  kanji: Kanji[] = []; //5
  vocab: Vocabulary[]=[]; //6
  reading: Reading[]=[]; //2
  kaiwa: Kaiwa[] =[];// 8
  listen: Listening[]=[]; //4
  writing: Writing[]=[]; //3
  gammar: Grammar[]=[];// 7
  listenQues: ListeningQuestion[] =[]; //9
  writingQues: WritingQuestion[]=[];  //10
  readingQues: ReadingQuestion[]=[]; //11

  constructor(private httpClient: HttpClient) { }

  getAllBook():Observable<Book[]>{
    return this.httpClient.get<Book[]>(`${this.baseURL}/book`);
  }

  getLessonById(id:number): Observable<Lesson>{
    return this.httpClient.get<Lesson>(`${this.baseURL}/lesson/${id}`);
  }

  getLessonByBook(id:number): Observable<Lesson[]>{
    return this.httpClient.get<Lesson[]>(`${this.baseURL}/book/${id}/lesson`);
  }

  getReadingByLesson(id:number): Observable<Reading[]>{
    return this.httpClient.get<Reading[]>(`${this.baseURL}/lesson/${id}/reading`);
  }

  getWritingByLesson(id:number): Observable<Writing[]>{
    return this.httpClient.get<Writing[]>(`${this.baseURL}/lesson/${id}/reading`);
  }

  getListeningByLesson(id: number): Observable<Listening[]>{
    return this.httpClient.get<Listening[]>(`${this.baseURL}/lesson/${id}/listening`);
  }

  getKanjiByLesson(id:number): Observable<Kanji[]>{
    return this.httpClient.get<Kanji[]>(`${this.baseURL}/lesson/${id}/kanji`);
  }

  getGrammarByLesson(id:number): Observable<Grammar[]>{
    return this.httpClient.get<Grammar[]>(`${this.baseURL}/lesson/${id}/grammar`);
  }

  getVocabByLesson(id:number): Observable<Vocabulary[]>{
    return this.httpClient.get<Vocabulary[]>(`${this.baseURL}/lesson/${id}/vocabulary`);
  }

  getKaiwaByLesson(id: number): Observable<Kaiwa[]>{
    return this.httpClient.get<Kaiwa[]>(`${this.baseURL}/lesson/${id}/kaiwa`);
  }

  getListeningQuesByListening(id: number): Observable<ListeningQuestion[]>{
    return this.httpClient.get<ListeningQuestion[]>(`${this.baseURL}/lesson/listening/${id}/listening-question`);
  }

  getWritingQuesByWriting(id: number) :Observable<WritingQuestion[]>{
    return this.httpClient.get<WritingQuestion[]>(`${this.baseURL}/lesson/writing/${id}/writing-question`);
  }

  getReadingQuesByReading(id: number): Observable<ReadingQuestion[]>{
    return this.httpClient.get<ReadingQuestion[]>(`${this.baseURL}/lesson/reading/${id}/reading-question`);
  }
}
