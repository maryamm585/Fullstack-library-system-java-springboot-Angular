import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable , throwError} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BookService {


  private baseUrl = 'http://localhost:8080/api/books';

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

  getAllBooks(): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}/booklist`, { headers });
  }

  searchBooks(keyword: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}/search`, {
      headers,
      params: { keyword }
    });
  }

  borrowBook(bookId: number, userId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/borrow`, null, {
      headers,
      params: { bookId, userid: userId.toString() }
    });
  }

  getBorrowedBooks(userId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}/borrowedBooks`, {
      headers,
      params: { userId: userId.toString() }
    });
  }
  
  returnBook(bookId: number, userId: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/return`, null, {
      headers,
      params: { bookId: bookId.toString(), userid: userId }
    });
  }
  
  getReviewsByBookId(bookId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get(`${this.baseUrl}/reviews/getreviewbyid`, {
      headers,
      params: { id: bookId.toString() }
    });
  }

  createReview(review: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/reviews`, review, { headers, responseType: 'text' });
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      if (error.status === 500) {
        errorMessage = 'A server error occurred. Please try again later.';
      } else if (error.status === 400) {
        errorMessage = 'Bad Request. Please check the data you provided.';
      } else if (error.status === 401) {
        errorMessage = 'Unauthorized. Please log in.';
      } else if (error.status === 403) {
        errorMessage = 'Forbidden. You do not have permission.';
      }
    }
    return throwError(errorMessage);
  }

  updateReview(id: string, review: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.baseUrl}/reviews/updatereview?id=${id}`, review, { headers });
  }

  createBook(book: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}`, book, { headers, responseType: 'text' });
  }  


  deleteBook(bookId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.baseUrl}/${bookId}`, { headers, responseType: 'text' });
  }


  deleteReview(reviewId: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.baseUrl}/reviews/${reviewId}`, { headers, responseType: 'text' });
  }
  


  }
