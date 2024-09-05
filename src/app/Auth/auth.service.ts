import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/authentication/Signup';
    constructor(private http: HttpClient) {}


    register(user: { username: string; password: string }): Observable<string> {
        return this.http.post<string>(this.apiUrl, user, { responseType: 'text' as 'json' });
    }
}

