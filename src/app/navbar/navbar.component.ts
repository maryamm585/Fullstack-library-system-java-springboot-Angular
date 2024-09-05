import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  isUser: boolean = false;
  isAdmin: boolean = false;

  constructor(private router: Router) { }

  decodeToken(token: string) {
    const payload = atob(token.split('.')[1]);
    return JSON.parse(payload);
  }

  ngOnInit() {
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;

    if (this.isLoggedIn) {
      const userRole = localStorage.getItem('userRole');
      this.isUser = userRole === 'User';
      this.isAdmin = userRole === 'Admin';
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

