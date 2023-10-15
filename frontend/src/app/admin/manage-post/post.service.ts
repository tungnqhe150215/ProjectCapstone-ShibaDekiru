import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from 'src/app/core/models/post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseURL = "http://localhost:8080/api/admin/post";

  post: Post[] = [];

  constructor(private httpClient: HttpClient) { }


  getPostlist(): Observable<Post[]>{
    return this.httpClient.get<Post[]>(`${this.baseURL}`);
  }

  getPostByID(id:number): Observable<Post>{
    return this.httpClient.get(`${this.baseURL}/${id}`);
  }

  createPost(post: Post): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`,post);
    
  }
  deletePost(id: number): Observable<Post>{
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }

  updatePost(id: number, post:Post): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}`,post);
  }

}
  

