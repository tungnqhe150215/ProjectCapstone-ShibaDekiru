import { Injectable } from '@angular/core';
import {Writing} from "../../core/models/writing";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageWritingService {

  private baseUrl="http://localhost:8080/api/admin/lesson";

  writings: Writing[] = [];

  constructor(private httpClient: HttpClient) { }

  getWritingByLesson(id:number): Observable<Writing[]>{
    return this.httpClient.get<Writing[]>(`${this.baseUrl}/${id}/writing`);
  }

  createWriting(id:number,writing:Writing): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/writing`,writing);
  }

  getWritingById(id:number): Observable<Writing>{
    return this.httpClient.get<Writing>(`${this.baseUrl}/writing/${id}`);
  }

  updateWriting(id:number,writing:Writing): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/writing/${id}`,writing);
  }

  deleteWriting(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/writing/${id}`);
  }
}
