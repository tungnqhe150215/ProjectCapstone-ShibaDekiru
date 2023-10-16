import { Injectable } from '@angular/core';
import {Reading} from "../../core/models/reading";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageReadingService {


  private baseUrl="http://localhost:8080/api/admin/lesson";

  readings: Reading[] = [];

  constructor(private httpClient: HttpClient) { }

  getReadingByLesson(id:number): Observable<Reading[]>{
    return this.httpClient.get<Reading[]>(`${this.baseUrl}/${id}/reading`);
  }

  createReading( id:number,reading: Reading): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/reading`,reading);
  }

  getReadingById(id:number): Observable<Reading>{
    return this.httpClient.get<Reading>(`${this.baseUrl}/reading/${id}`);
  }

  updateReading(id:number,reading:Reading): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/reading/${id}`,reading);
  }

  deleteReading(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/reading/${id}`);
  }
}
