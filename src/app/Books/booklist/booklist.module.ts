import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { BooklistComponent } from './booklist.component';
import { BooklistRoutingModule} from './booklist-routing.module'


@NgModule({
 declarations: [BooklistComponent],
  imports: [
    CommonModule,
    BooklistRoutingModule,
    ReactiveFormsModule,
  ]
})
export class BooklistModule { }
