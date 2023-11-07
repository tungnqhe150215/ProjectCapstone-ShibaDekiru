import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/core/models/comment';
import { Post } from 'src/app/core/models/post';
import { UserAccount } from 'src/app/core/models/user-account';

@Injectable({
  providedIn: 'root'
})
export class UserPostService {

  private idPost !:number;

  setPostID(id: number){
    this.idPost = id;
  }

  getPostID(){
    return this.idPost;
  }

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
  
  createComment(id:number , userID:number, comment:Comment): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/${id}/comment/${userID}`,comment);
  }

  updateComment(id: number,userID:number, comtId:number , comment:Comment):Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}/comment/${userID}/${comtId}`,comment);
  }

  deleteComment(id: number,userID:number, comtId:number):Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/${id}/comment/${userID}/${comtId}`);
  }

}
