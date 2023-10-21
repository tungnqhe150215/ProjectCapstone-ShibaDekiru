import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { LecturersRoutingModule } from './lecturers-routing.module';
import { CreatePostComponent } from './lecturers-post/create-post/create-post.component';
import { UpdatePostComponent } from './lecturers-post/update-post/update-post.component';
import { ListPostComponent } from './lecturers-post/list-post/list-post.component';
import { ViewPostComponent } from './lecturers-post/view-post/view-post.component';
import { NavsideComponent } from './navside/navside.component';
import { RouterModule } from '@angular/router';



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
    // LecturersRoutingModule,
    RouterModule
  ]
})
export class LecturersModule { }
