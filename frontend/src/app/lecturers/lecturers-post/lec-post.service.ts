import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from 'src/app/core/models/post';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/core/models/comment';

@Injectable({
  providedIn: 'root'
})
export class LecPostService {

  private baseURL = "http://localhost:8080/api/admin/post";
  private baseLecURL = "http://localhost:8080/api/lecture";
  comment: Comment[] =[];
  private baseCommentURL = "http://localhost:8080/api/post";
  post: Post[] = [];

  constructor(private httpClient: HttpClient) { }

  getlistByUser(id: number): Observable<Post[]>{
    return this.httpClient.get<Post[]>(`${this.baseLecURL}/${id}/post`);
  }

  getPostlist(): Observable<Post[]>{
    return this.httpClient.get<Post[]>(`${this.baseURL}`);
  }

  getPostByID(id:number): Observable<Post>{
    return this.httpClient.get<Post>(`${this.baseLecURL}/post/${id}`);
  }

  addPost(id: number, post: Post): Observable<Object>{
    return this.httpClient.post(`${this.baseLecURL}/${id}/post`,post);
    
  }

  createPost(post: Post): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`,post);
    
  }
  deletePost(id: number): Observable<Object>{
    return this.httpClient.delete(`${this.baseLecURL}/post/${id}`);
  }

  updatePost(id: number, post:Post): Observable<Object>{
    return this.httpClient.put(`${this.baseLecURL}/post/${id}`,post);
  }

  getAllComment(id:number ): Observable<Comment[]>{
    return this.httpClient.get<Comment[]>(`${this.baseCommentURL}/${id}/comment`);
  }

  deleteComment(id: number,userID:number, comtId:number):Observable<Object>{
    return this.httpClient.delete(`${this.baseCommentURL}/${id}/comment/${userID}/${comtId}`);
  }
}
