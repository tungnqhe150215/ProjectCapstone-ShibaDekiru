import { Injectable } from '@angular/core';
import {Listening} from "../../core/models/listening";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageListeningService {
  private baseUrl="http://localhost:8080/api/admin/lesson";

  listenings: Listening[] = [];

  constructor(private httpClient: HttpClient) { }

  getListeningByLesson(id:number): Observable<Listening[]>{
    return this.httpClient.get<Listening[]>(`${this.baseUrl}/${id}/listening`);
  }

  createListening( id:number,listening: Listening): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/listening`,listening);
  }

  getListeningById(id:number): Observable<Listening>{
    return this.httpClient.get<Listening>(`${this.baseUrl}/listening/${id}`);
  }

  updateListening(id:number,listening:Listening): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/listening/${id}`,listening);
  }

  deleteListening(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/listening/${id}`);
  }
}
