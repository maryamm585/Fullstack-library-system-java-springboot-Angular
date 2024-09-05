import { Component } from '@angular/core';
import { BookService } from '../book.service';
import { debounceTime, switchMap , tap ,catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';


@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrl: './booklist.component.css'
})
export class BooklistComponent {
  books: any[] = [];
  searchControl = new FormControl('');
  isLoggedIn: boolean = false;
  isUser: boolean = false;
  isAdmin: boolean = false;
  noResultsFound: boolean = false;
 
  
  constructor(private bookService: BookService, private router: Router) {}

  ngOnInit(): void {
    this.savedata();
    this.getAllBooks();


    this.searchControl.valueChanges.pipe(
      debounceTime(300), // Wait for 300ms pause in events
      switchMap(keyword => {
        const searchKeyword = keyword?.toString().trim() || '';
        if (searchKeyword === '') {
          this.noResultsFound = false; // Reset the no results flag
          return this.bookService.getAllBooks();
        } else {
          return this.bookService.searchBooks(searchKeyword).pipe(
            tap(response => {
              if (response.length === 0) {
                this.noResultsFound = true; // Set flag to true when no results found
              } else {
                this.noResultsFound = false; // Reset the flag if results are found
              }
            }),
            catchError(error => {
              console.error('Error fetching books:', error);
              this.noResultsFound = false; // Reset the flag on error
              return of([]); // Return an empty array in case of error
            })
          );
        }
      })
    ).subscribe(
      (response: any) => {
        this.books = response.content || response;
      },
      (error) => {
        console.error('Error fetching books:', error);
      }
    );
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

  getAllBooks(): void {
    this.bookService.getAllBooks().subscribe(
      (response) => {
        this.books = response.content;
      },
      (error) => {
        console.error('Error fetching books:', error);
      }
    );

  }


  borrowBook(bookId: number): void {
    const userId = Number(localStorage.getItem('userId')); // Adjust based on how you store userId
    if (userId) {
      this.bookService.borrowBook(bookId, userId).subscribe(
        (response) => {
          console.log('Book borrowed successfully:', response);
          // Optionally refresh the book list or show a success message
        },
        (error) => {
          console.error('Error borrowing book:', error);
        }
      );
    } else {
      console.error('User not logged in');
    }
  }

  viewReviews(bookId: number): void {
    this.router.navigate(['/reviews', bookId]);
  }

  navigateToAddReview(bookId: number): void {
    this.router.navigate(['/add-review', bookId]);
  }

  navigateToAddBook(): void {
    this.router.navigate(['/add-book']);
  }


  deleteBook(bookId: number): void {
    if (confirm('Are you sure you want to delete this book?')) {
      this.bookService.deleteBook(bookId).subscribe(
        (response) => {
          console.log('Book deleted successfully:', response);
          // Remove the deleted book from the list
          this.books = this.books.filter(book => book.id !== bookId);
        },
        (error) => {
          alert('Cannot delete the book it is currently borrowed.');
        }
      );
    }
  }



  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('userRole');
    this.isLoggedIn = false;
    this.isUser = false;
    this.isAdmin = false;
    this.router.navigate(['/']);
  }

}