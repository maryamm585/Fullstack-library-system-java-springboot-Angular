import { Component } from '@angular/core';
import { BookService } from '../book.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent {
  book: any = {};  // Object to hold book data from the form
  errorMessage: string | null = null;  // Property to store error messages
  successMessage: string | null = null;  // Property to store success messages

  constructor(private bookService: BookService) {}

  onSubmit() {
    this.bookService.createBook(this.book).subscribe({
      next: (response) => {
        this.successMessage = 'Book added successfully!';
        this.errorMessage = null;
      },
      error: (error) => {
        this.errorMessage = 'Failed to add the book. Please try again.';
        this.successMessage = null;
      }
    });
  }
}
