import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule ,NgForm } from '@angular/forms';
import { AuthServicelogin } from '../authlogin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage: string = '';
  constructor(private router: Router, private authService: AuthServicelogin) { }

  onSubmit(form: NgForm) {
    if (form.valid) {
      const loginData = {
        username: form.value.username,
        password: form.value.password
      };

      this.authService.login(loginData).subscribe(
        (response: any) => {
          // Save the token and user role in local storage
          localStorage.setItem('token', response.jwtToken);
          localStorage.setItem('userId', response.userId);
          localStorage.setItem('userRole', response.userRole);
          alert("user loged iin successfully");
          this.errorMessage = ''; // Clear any previous error message
          this.router.navigate(['/']); // Redirect to home or another page
        },
        error => {
          if (error.status === 400) {
          this.errorMessage = "Invalid username or password";
        } else {
          this.errorMessage = "An error occurred. Please try again.";
        }}
      );
    }
  }


}

