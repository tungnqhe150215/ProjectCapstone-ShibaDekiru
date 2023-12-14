import { Injectable } from '@angular/core';
import {TestSection} from "../../core/models/test-section";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Drive} from "../../core/models/drive";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl = "http://localhost:8080/api/drive";

  constructor(private httpClient : HttpClient) { }

  uploadFile(file: File): Observable<Object> {
    const formData = new FormData();
    formData.append('file', file);
    console.log(formData.get('file'))
    return this.httpClient.post(`${this.baseUrl}/upload`,formData);
  }

  uploadFileForMyProfile(file: File): Observable<Object> {
    const formData = new FormData();
    formData.append('file', file);
    console.log(formData.get('file'))
    return this.httpClient.post(`${this.baseUrl}/upload/my-profile`,formData);
  }

}
