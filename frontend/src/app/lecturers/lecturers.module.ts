import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { LecturersRoutingModule } from './lecturers-routing.module';
import { CreatePostComponent } from './lecturers-post/create-post/create-post.component';
import { UpdatePostComponent } from './lecturers-post/update-post/update-post.component';
import { ListPostComponent } from './lecturers-post/list-post/list-post.component';
import { ViewPostComponent } from './lecturers-post/view-post/view-post.component';
import { NavsideComponent } from './navside/navside.component';
import { RouterModule } from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    CreatePostComponent,
    UpdatePostComponent,
    ListPostComponent,
    ViewPostComponent,
    NavsideComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    // LecturersRoutingModule,
    RouterModule,
    MatFormFieldModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    MatIconModule,
    MatInputModule,
    FormsModule,
  ]
})
export class LecturersModule { }
