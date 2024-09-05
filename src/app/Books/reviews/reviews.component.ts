import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../book.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {
  reviews: any[] = [];
  bookId: number=0;
  isUser:boolean=false;
  isAdmin:boolean=false;
  currentuserid= Number(localStorage.getItem('userId'));

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('bookId'));
    this.checkrole();
    this.getReviews();
  }
  checkrole(): void {
    const userRole = localStorage.getItem('userRole');
    this.isUser = userRole === 'User';
    this.isAdmin = userRole === 'Admin';
  }

  getReviews(): void {
    this.bookService.getReviewsByBookId(this.bookId).subscribe(
      (response) => {
        this.reviews = response;
      },
      (error) => {
        console.error('Error fetching reviews:', error);
      }
    );
  }
  canUpdateReview(review: any): boolean {
    return review.userId === this.currentuserid; 
  }

  openUpdateDialog(review: any): void {
    const newRating = prompt('Enter new rating:', review.rating);
    const newReviewText = prompt('Enter new review text:', review.reviewText);
    
    if (newRating !== null && newReviewText !== null) {
      this.updateReview(review.id, newRating, newReviewText);
    }
  }

  updateReview(id: string, rating: string, reviewText: string): void {
    const updatedReview = {
      ...this.reviews.find(review => review.id === id),
      rating: Number(rating),
      reviewText: reviewText
    };

    this.bookService.updateReview(id, updatedReview).subscribe(
      () => {
        console.log('Review updated successfully');
        this.getReviews(); 
      },
      (error) => {
        console.error('Error updating review:', error);
      }
    );
  }


  deleteReview(id: string): void {
    if (confirm('Are you sure you want to delete this review?')) {
      this.bookService.deleteReview(id).subscribe(
        () => {
          console.log('Review deleted successfully');
          this.getReviews(); // Refresh the list of reviews
        },
        (error) => {
          console.error('Error deleting review:', error);
        }
      );
    }
  }

}
