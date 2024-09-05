import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormsModule ,NgForm } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {
    console.log("SignupComponent");
   }

   onSubmit(form: NgForm) {
    if (form.valid) {
      const user = {
        username: form.value.username,
        password: form.value.password
      };
      this.authService.register(user).subscribe(
        response => {
          this.successMessage = response; 
          this.errorMessage = ''; 
          alert(response); 
          this.router.navigate(['/login']);
        },
        error => {
          this.errorMessage = error.error || 'An error occurred'; 
        }
      );
    }
  }
}