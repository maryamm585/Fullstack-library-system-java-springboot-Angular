import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable , throwError} from 'rxjs';
import { debounceTime, switchMap, tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { User } from './user.model';

@Injectable({
    providedIn: 'root'
  })

export class UserService {

    private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  private getToken(): string | null {
  
    return localStorage.getItem('token'); 
  }


  private getHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllUsers(): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}`, { headers });
  }


  deleteUser(userId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.baseUrl}/${userId}`, { headers, responseType: 'text' });
  }

  
  assignRoleToUser(userid: number, roleName: string): Observable<string> {
    const url = `${this.baseUrl}/${userid}/assignrole?roleName=${roleName}`;
    const headers = this.getHeaders();
    return this.http.post<string>(url, null, { headers });
  }


  searchUsers(username: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}/search`, {
      headers,
      params: { username }
    });
  }

}