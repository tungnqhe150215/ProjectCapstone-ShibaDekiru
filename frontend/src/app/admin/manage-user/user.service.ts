import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAccount } from 'src/app/core/models/user-account';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = "http://localhost:8080/api/admin/user-account";
  private baseBanURL = "http://localhost:8080/api/admin/user-account/update-is-banned";
  user_account: UserAccount[] = [];

  constructor(private httpClient: HttpClient) { }

  getUserAccountList(): Observable<UserAccount[]>{
    return this.httpClient.get<UserAccount[]>(`${this.baseURL}`);
  }

  createUser(userAccount: UserAccount): Observable<Object>{
    return this.httpClient.post((`${this.baseURL}`),userAccount);
  }

  getUserByID(id: number): Observable<UserAccount>{
    return this.httpClient.get<UserAccount>(`${this.baseURL}/${id}`);
  }

  updateUser(id:number, userAccount: UserAccount): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}`,userAccount);
  }

  banAccountUser(id:number){
    return this.httpClient.put<void>(`${this.baseBanURL}/${id}`,{});
  }

}
