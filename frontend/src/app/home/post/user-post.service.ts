import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/core/models/comment';
import { Post } from 'src/app/core/models/post';
import { UserAccount } from 'src/app/core/models/user-account';
import {Page} from "../../core/models/page";

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

  getAllPost(page: number, size: number, keyword: string): Observable<Page<Post>>{
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('keyword', keyword.toString());
    return this.httpClient.get<Page<Post>>(this.baseURL, {params});
  }

  getPostByID(id:number): Observable<Post>{
    return this.httpClient.get<Post>(`${this.baseURL}/${id}`);
  }

  getAllComment(id:number, page: number, size: number): Observable<Page<Comment>>{
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.httpClient.get<Page<Comment>>(`${this.baseURL}/${id}/comment`, {params});
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

  getLatestPost(): Observable<Post[]>{
    return this.httpClient.get<Post[]>(`${this.baseURL}/4lastest`);
  }
}
