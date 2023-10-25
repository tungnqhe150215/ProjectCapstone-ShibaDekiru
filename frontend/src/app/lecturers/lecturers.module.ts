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
import { ClassworkComponent } from './classwork/classwork.component';
import {MatTabsModule} from '@angular/material/tabs';
import { AddClassworkComponent } from './classwork/add-classwork/add-classwork.component';
import { UpdateClassworkComponent } from './classwork/update-classwork/update-classwork.component';
import { DeleteClassworkComponent } from './classwork/delete-classwork/delete-classwork.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import { ClassworkDetailComponent } from './classwork/classwork-detail/classwork-detail.component';

@NgModule({
  declarations: [
    CreatePostComponent,
    UpdatePostComponent,
    ListPostComponent,
    ViewPostComponent,
    NavsideComponent,
    ClassworkComponent,
    AddClassworkComponent,
    UpdateClassworkComponent,
    DeleteClassworkComponent,
    ClassworkDetailComponent
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
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    
  ]
})
export class LecturersModule { }
