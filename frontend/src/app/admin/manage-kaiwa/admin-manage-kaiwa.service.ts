import { Injectable } from '@angular/core';
import {Kaiwa} from "../../core/models/kaiwa";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AdminManageKaiwaService {

  private baseUrl="http://localhost:8080/api/admin/lesson";

  kaiwas: Kaiwa[] = [];

  constructor(private httpClient: HttpClient) { }

  getKaiwaByLesson(id:number): Observable<Kaiwa[]>{
    return this.httpClient.get<Kaiwa[]>(`${this.baseUrl}/${id}/kaiwa`);
  }

  createKaiwa( id:number,kaiwa: Kaiwa): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}/${id}/kaiwa`,kaiwa);
  }

  getKaiwaById(id:number): Observable<Kaiwa>{
    return this.httpClient.get<Kaiwa>(`${this.baseUrl}/kaiwa/${id}`);
  }

  updateKaiwa(id:number,kaiwa:Kaiwa): Observable<Object>{
    return this.httpClient.put(`${this.baseUrl}/kaiwa/${id}`,kaiwa);
  }

  deleteKaiwa(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseUrl}/kaiwa/${id}`);
  }
}
