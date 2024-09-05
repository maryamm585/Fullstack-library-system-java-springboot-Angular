import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user.model';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { debounceTime, switchMap , tap ,catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { FormControl } from '@angular/forms';



@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  users: any[] = [];
  isLoggedIn: boolean = false;
  isUser: boolean = false;
  isAdmin: boolean = false;
  noResultsFound: boolean = false;
  searchControl = new FormControl(''); // Define searchControl

  constructor(private userService: UserService, private router: Router,private http: HttpClient) {}

  ngOnInit(): void {

    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      switchMap(username => {
        const searchUsername = username?.toString().trim() || '';
        if (searchUsername === '') {
          // If search input is cleared, reload all users
          this.noResultsFound = false;
          return this.userService.getAllUsers().pipe(
            tap(response => this.users = response),
            catchError(error => {
              console.error('Error fetching users:', error);
              return of([]);
            })
          );
        } else {
          // Perform search based on input
          return this.userService.searchUsers(searchUsername).pipe(
            tap(response => {
              this.users = response;
              this.noResultsFound = response.length === 0;
            }),
            catchError(error => {
              console.error('Error searching users:', error);
              this.noResultsFound = false;
              return of([]);
            })
          );
        }
      })
    ).subscribe();

    this.getAllUsers();
    this.savedata();
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

  getAllUsers(): void {
    this.userService.getAllUsers().subscribe(
      (data: User[]) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  deleteUser(userId: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(userId).subscribe(
        (response) => {
          alert(response); // Display success message
          this.getAllUsers(); // Refresh the user list after deletion
        },
        (error) => {
          console.error('Error deleting user:', error);
          alert('Failed to delete the user.');
        }
      );
    }}

    toggleDropdown(userId: number) {
      this.users.forEach(user => {
        if (user.id === userId) {
          user.showDropdown = !user.showDropdown;
        } else {
          user.showDropdown = false; // Close other dropdowns
        }
      });
    }
  
    assignRole(userId: number, roleName: string) {
      this.userService.assignRoleToUser(userId, roleName).subscribe(
        (response) => {
          this.users.find(user => user.id === userId).responseMessage = response;
          this.getAllUsers();
          this.toggleDropdown(userId); // Close dropdown after selection
          
          },
        (error) => {
          console.error('Error assigning role:', error);
          this.users.find(user => user.id === userId).responseMessage = 'An error occurred: ' + (error.error?.message || error.message);
        }
      );}

  
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
