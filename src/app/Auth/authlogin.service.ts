import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthServicelogin {

    private baseUrl  = 'http://localhost:8080/authentication/login';
    constructor(private http: HttpClient) { }
    login(loginData: any): Observable<any> {
      const url = `${this.baseUrl}?username=${loginData.username}&password=${loginData.password}`;
        return this.http.post(url, loginData);
    }


}