import { Injectable } from '@angular/core';
import {ListeningQuestion} from "../../core/models/listening-question";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageListeningQuestionService {
  private baseUrl="http://localhost:8080/api/admin/lesson/listening";

  listeningQuestion: ListeningQuestion[] = [];

  constructor(private httpClient: HttpClient) { }

  getlisteningQuestionByListening(id:number): Observable<ListeningQuestion[]>{
    return this.httpClient.get<ListeningQuestion[]>(`${this.baseUrl}/${id}/listening-question`);
  }

  createListeningQuestion(id:number,listeningQuestion:ListeningQuestion): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/listening-question`,listeningQuestion);
  }

  getListeningQuestionById(id:number): Observable<ListeningQuestion>{
    return this.httpClient.get<ListeningQuestion>(`${this.baseUrl}/listening-question/${id}`);
  }

  updateListeningQuestion(id:number,listeningQuestion:ListeningQuestion): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/listening-question/${id}`,listeningQuestion);
  }

  deleteListeningQuestion(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/listening-question/${id}`);
  }
}
