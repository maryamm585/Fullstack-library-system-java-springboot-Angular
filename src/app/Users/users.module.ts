import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { debounceTime, switchMap, tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { UserListComponent } from './user-list/user-list.component';
import { UsersRoutingModule } from './users-routing.module';
import { ReactiveFormsModule } from '@angular/forms';  // Import this module



@NgModule({
  declarations: [UserListComponent],
  imports: [
    CommonModule,
    UsersRoutingModule,
    ReactiveFormsModule,
  ]
})
export class UsersModule { }
