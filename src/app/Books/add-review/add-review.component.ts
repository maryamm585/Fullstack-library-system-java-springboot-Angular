import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../book.service';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {
  bookId: number=0;
  review = {
    rating: 0,
    reviewText: ''
  };
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('bookId'));
  }
  addReview(): void {
    const userId = Number(localStorage.getItem('userId'));
    if (!userId) {
      this.errorMessage = 'You must be logged in to add a review.';
      return;
    }
  
    const newReview = {
      ...this.review,
      bookId: this.bookId,
      userId: userId,
      reviewDate: new Date() 
    };
  
    this.bookService.createReview(newReview).subscribe(
      (response: string) => {  // Expecting a string response
        this.successMessage = response;  // Use the response from the backend
        this.errorMessage = '';
        this.router.navigate(['/booklist']);
      },
      (error) => {
        console.error('Error adding review:', error);
        this.errorMessage = 'An error occurred while adding the review.';
        this.successMessage = '';
      }
    );
  }
}