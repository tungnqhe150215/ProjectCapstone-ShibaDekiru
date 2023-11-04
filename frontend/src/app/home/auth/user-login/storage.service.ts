import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';


const USER_KEY = 'auth-user';
// const token = localStorage.getItem('USER_KEY');
// const httpOptions = {
//   headers: new HttpHeaders({ 'Content-Type': 'application/json',   'Authorization': 'Bearer Token ' + token })
// };


@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }




  clean(): void {
    window.sessionStorage.clear();
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    console.log(window.sessionStorage.getItem(USER_KEY))
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return null;
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }

    return false;
  }

}
