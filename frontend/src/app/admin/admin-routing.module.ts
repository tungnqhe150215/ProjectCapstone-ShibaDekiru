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
import {ListWritingComponent} from "./manage-writing/list-writing/list-writing.component";
import {WritingDetailComponent} from "./manage-writing/writing-detail/writing-detail.component";
import {
  WritingQuestionDetailComponent
} from "./manage-writing/writing-question-detail/writing-question-detail.component";
import { UpdateLessonComponent } from './manage-lesson/update-lesson/update-lesson.component';
import { CreateLessonComponent } from './manage-lesson/create-lesson/create-lesson.component';
import { PostDetailComponent } from './manage-post/post-detail/post-detail.component';
import { ListKanjiComponent } from './manage-kanji/list-kanji/list-kanji.component';
import { ListHiraganaComponent } from './manage-alphabet/list-hiragana/list-hiragana.component';
import { ListKatakanaComponent } from './manage-alphabet/list-katakana/list-katakana.component';
import { CreateKanjiComponent } from './manage-kanji/create-kanji/create-kanji.component';



const routes: Routes = [
  {path: 'admin', component: SidebarComponent,
    children:[
      {path: 'lesson', component:ListLessonComponent},
      {path: 'lesson/create-lesson', component:CreateLessonComponent},
      {path: 'lesson/update-lesson/:id', component: UpdateLessonComponent},
      {path: 'lesson/lesson-detail/:id', component:LessonDetailComponent},
      {path:'post', component:ListPostComponent},
      {path:'post/post-detail/:id', component:PostDetailComponent },
      { path:'list-user', component:UserListComponent},
      { path:'list-chat', component:ListChatComponent},
      { path:'list-book', component:ListBookComponent},
      { path:'list-kanji', component:ListKanjiComponent},
      { path:'lesson/:id/writing',component:ListWritingComponent},
      { path:'lesson/writing/:id',component:WritingDetailComponent},
      { path:'lesson/writing/writing-question/:id',component:WritingQuestionDetailComponent},
      {path:'list-hiragana',component:ListHiraganaComponent},
      {path:'list-katakana',component:ListKatakanaComponent},
      {path:'create-kanji',component:CreateKanjiComponent},
  ]},



  // {path: 'admin/list-post' , component: ListPostComponent},

];
@NgModule({
  // declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
