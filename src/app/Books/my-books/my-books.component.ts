import { Component } from '@angular/core';
import { BookService } from '../book.service';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.css'
})
export class MyBooksComponent {
  borrowedBooks: any[] = [];
  isLoggedIn: boolean = false;
  isUser: boolean = false;
  isAdmin: boolean = false;
  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.savedata();
    this.getBorrowedBooks();
    
  }

  savedata(){
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;

    if (this.isLoggedIn) {
      const userRole = localStorage.getItem('userRole');
      this.isUser = userRole === 'User';
      this.isAdmin = userRole === 'Admin';
    }
  }


    getBorrowedBooks(): void {
      const userId = Number(localStorage.getItem('userId')); 
      if (userId) {
        this.bookService.getBorrowedBooks(userId).subscribe(
          (response) => {
            this.borrowedBooks = response;
            console.log('Fetched Borrowed Books:', this.borrowedBooks);
          },
          (error) => {
            console.error('Error fetching borrowed books:', error);
          }
        );
      } else {
        console.error('User not logged in');
      }
    }

    returnBook(bookId: number): void {
      console.log('Return button clicked for bookId:', bookId);
      const userId = localStorage.getItem('userId');
      if (userId) {
        this.bookService.returnBook(bookId, userId).subscribe(
          (response) => {
            // Successfully returned the book
            console.log('Book returned successfully');
            // Remove the book from the list
            this.borrowedBooks = this.borrowedBooks.filter(book => book.id !== bookId);
            this.getBorrowedBooks();
          },
          (error) => {
            console.error('Error returning book:', error);
          }
        );
      } else {
        console.error('User not logged in');
      }
    }
    
  

    
}
