import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  getJsonData(key: string): any {
    const data = sessionStorage.getItem(key);
    if (data) {
      try {
        return JSON.parse(data);
      } catch (e) {
        console.error('Error parsing JSON data from sessionStorage:', e);
        return null;
      }
    }
    return null;
  }
}
