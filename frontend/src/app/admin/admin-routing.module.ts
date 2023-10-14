import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ListPostComponent} from "./manage-post/list-post/list-post.component";
import { ListLessonComponent } from './manage-lesson/list-lesson/list-lesson.component';
import { ListChatComponent } from './manage-chat/list-chat/list-chat.component';
import { UserListComponent } from './manage-user/user-list/user-list.component';
import { LessonDetailComponent } from './manage-lesson/lesson-detail/lesson-detail.component';

import { SidebarComponent } from './sidebar/sidebar.component';
import { ListBookComponent } from './manage-book/list-book/list-book.component';


const routes: Routes = [
  {path: 'admin', component: SidebarComponent,
    children:[
      {path:'list-post', component:ListPostComponent},
      {path: 'lesson', component:ListLessonComponent},
      {path: 'lesson-detail', component:LessonDetailComponent},
      { path:'list-user', component:UserListComponent},
      { path:'list-chat', component:ListChatComponent},
      { path:'list-book', component:ListBookComponent},
  ]},
  

 
  // {path: 'admin/list-post' , component: ListPostComponent},
  
];
@NgModule({
  // declarations: [],
  imports: [RouterModule.forChild(routes)], 
  exports: [RouterModule]
})
export class AdminRoutingModule { }
