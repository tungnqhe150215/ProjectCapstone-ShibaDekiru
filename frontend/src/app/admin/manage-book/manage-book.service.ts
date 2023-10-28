import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from 'src/app/core/models/book';

@Injectable({
  providedIn: 'root'
})
export class ManageBookService {

  private baseURL = 'http://localhost:8080/api/admin/book';

  book: Book[] = [];
  constructor(private httpClient: HttpClient) { }

  getAllBook(): Observable<Book[]>{
    return this.httpClient.get<Book[]>(`${this.baseURL}`);
  }

  getBookByID(id:number): Observable<Book>{
    return this.httpClient.get<Book>(`${this.baseURL}/${id}`);
  }

  createBook(book: Book): Observable<Object>{
    return this.httpClient.post((`${this.baseURL}`),book);
  }

  updateBook(id:number, book:Book): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}`,book);
  }

  deleteBook(id:number):Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}

