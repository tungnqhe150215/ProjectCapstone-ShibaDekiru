import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/core/models/comment';
import { Post } from 'src/app/core/models/post';

@Injectable({
  providedIn: 'root'
})
export class UserPostService {

  post: Post[]=[];
  comment: Comment[] =[];
  private baseURL = "http://localhost:8080/api/post";
  constructor(private httpClient: HttpClient) { }

  getAllPost(): Observable<Post[]>{
    return this.httpClient.get<Post[]>(`${this.baseURL}`);
  }
  
  getPostByID(id:number): Observable<Post>{
    return this.httpClient.get<Post>(`${this.baseURL}/${id}`);
  }

  getAllComment(id:number ): Observable<Comment[]>{
    return this.httpClient.get<Comment[]>(`${this.baseURL}/${id}/comment`);
  }
  
}
