import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './Auth/signup/signup.component';
import { NavbarComponent } from './navbar/navbar.component';
 import { LoginComponent } from './Auth/login/login.component';
import { MyBooksComponent } from './Books/my-books/my-books.component';
import { ReviewsComponent } from './Books/reviews/reviews.component';
import { AddReviewComponent } from './Books/add-review/add-review.component';
import { AddBookComponent } from './Books/add-book/add-book.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'login', component: LoginComponent },
    { path: 'booklist', loadChildren: () => import('./Books/booklist/booklist.module').then(m => m.BooklistModule) },
    { path: 'my-books', component: MyBooksComponent },
    { path: 'reviews/:bookId', component: ReviewsComponent },
    { path: 'add-review/:bookId', component: AddReviewComponent },
    { path: 'add-book', component: AddBookComponent },
    { path: 'users', loadChildren:() => import('./Users/users.module').then(m => m.UsersModule) },
];

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        SignupComponent,
        NavbarComponent,
        LoginComponent,
        MyBooksComponent,
        ReviewsComponent,
        AddReviewComponent,
        AddBookComponent,

    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
        RouterModule.forRoot(routes),
        CommonModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
