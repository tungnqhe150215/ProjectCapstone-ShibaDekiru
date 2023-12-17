import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ChangePassword} from 'src/app/core/models/change-password';
import {ForgotPassword} from 'src/app/core/models/forgot-password';
import {Lecture} from 'src/app/core/models/lecture';
import {NewPassword} from 'src/app/core/models/new-password';
import {Student} from 'src/app/core/models/student';
import {UserAccount} from "../../core/models/user-account";

const USER_KEY = 'auth-user';
const AUTH_API = 'http://localhost:8080/api/auth/';
const USER = 'http://localhost:8080/api/user-account/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UseServiceService {
  constructor(private httpClient: HttpClient) {
  }

  login(email: string, password: string): Observable<any> {
    return this.httpClient.post(
      AUTH_API + 'authenticate',
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

  register(nickName: string, firstName: string, lastName: string, memberId: string, email: string, password: string, role: {
    roleId: String
  }): Observable<any> {
    return this.httpClient.post(
      AUTH_API + 'register', {
        nickName,
        firstName,
        lastName,
        memberId,
        email,
        password,
        role
      },
      httpOptions
    );
  }

  register1(nickName: string, firstName: string, lastName: string, memberId: string, email: string, password: string, roleId: number): Observable<Object> {
    return this.httpClient.post(
      AUTH_API + 'register', {
        nickName,
        firstName,
        lastName,
        memberId,
        email,
        password,
        roleId
      },
      httpOptions
    );
  }

  logout(): Observable<any> {
    return this.httpClient.post(AUTH_API + 'logout', {}, httpOptions);
  }


  getUserAccountByUserId(id: string): Observable<UserAccount> {
    const params = new HttpParams().set("userId", id);
    return this.httpClient.get<UserAccount>(AUTH_API + 'user-account', {params: params});
  }

  getLectureByUserId(id: number | undefined): Observable<Lecture> {
    // @ts-ignore
    const params = new HttpParams().set("lectureId", id);
    return this.httpClient.get<Lecture>(AUTH_API + 'user-account/lecture', {params: params});
  }

  updateUserAccount(id: number, nickName: string): Observable<Object> {
    const params = new HttpParams().set("userId", id).set("nickName", nickName);
    return this.httpClient.put(AUTH_API + 'user-account', {}, {params: params});
  }

  changePassword(changePassword: ChangePassword): Observable<any> {
    return this.httpClient.put(USER + 'change-password', changePassword);
  }

  studentProfile(infoStudent: Student): Observable<Object> {
    return this.httpClient.put(USER + 'student/my-profile', infoStudent);
  }

  lecturerProfile(inforLecturers: Lecture): Observable<Object> {
    return this.httpClient.put(USER + 'lecture/my-profile', inforLecturers);
  }

  getStudentbyID(id: number): Observable<Student> {
    return this.httpClient.get<Student>(USER + 'student/' + id);
  }

  getLecturersbyID(id: number): Observable<Lecture> {
    return this.httpClient.get<Lecture>(USER + 'lecture/' + id);
  }

  forgotPassword(inforEmail: ForgotPassword): Observable<Object> {
    return this.httpClient.post(AUTH_API + 'forgot-password', inforEmail);
  }

  verifyUserByResetCode(resetCode: string) {
    return this.httpClient.get(AUTH_API + 'reset-password/' + `${resetCode}`);
  }

  resetPassword(resetCode: string, inforPassword: NewPassword): Observable<NewPassword> {
    return this.httpClient.post<NewPassword>(AUTH_API + 'reset-password/' + `${resetCode}`, inforPassword);
  }

  verifyEmail(resetCode: string): Observable<any> {
    return this.httpClient.get<any>(AUTH_API + 'verify/' + `${resetCode}`);
  }

  updateImage(file: File): Observable<Object> {
    const formData = new FormData();
    formData.append('file', file);
    console.log(formData.get('file'))
    return this.httpClient.post(USER + 'upload', file);
  }
}
