import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

const USER_KEY = 'auth-user';
const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'  })
};
@Injectable({
  providedIn: 'root'
})
export class UseServiceService {
  constructor(private httpClient: HttpClient) { }

  login(email:string, password: string): Observable<any>{
    return this.httpClient.post(
      AUTH_API +'authenticate',
      {
        email,
        password,
      },
      httpOptions
    )
    // .pipe(
    //   map((res:any) =>{
    //     console.log('result', res);
    //     return res;
    //   })
    // )
  }
  register(nickName:string, firstName: string,lastName:string,  memberId:string,  userName: string , email:string,password:string ): Observable<any>{
    return this.httpClient.post(
      AUTH_API + 'register',{
        nickName,
        firstName,
        lastName,
        memberId,
        userName,
        email,
        password
      },
      httpOptions
    );
  }
  logout():Observable<any>{
    return this.httpClient.post(AUTH_API + 'logout', {}, httpOptions);
  }



}
