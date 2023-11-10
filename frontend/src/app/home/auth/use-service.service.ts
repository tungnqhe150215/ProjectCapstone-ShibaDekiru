import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ChangePassword } from 'src/app/core/models/change-password';
import { Lecture } from 'src/app/core/models/lecture';
import { Student } from 'src/app/core/models/student';

const USER_KEY = 'auth-user';
const AUTH_API = 'http://localhost:8080/api/auth/';
const USER = 'http://localhost:8080/api/user-account/';

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


  changePassword(changePassword: ChangePassword): Observable<any>{
    return this.httpClient.put(USER +'change-password', changePassword);
  }

  
}
