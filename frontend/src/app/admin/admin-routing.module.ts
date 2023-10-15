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
import { ListKanjiComponent } from './manage-kanji/list-kanji/list-kanji.component';
import { VocabularyListComponent } from './manage-vocabulary/vocabulary-list/vocabulary-list.component';
import { ListGrammarComponent } from './manage-grammar/list-grammar/list-grammar.component';
import { CreateKanjiComponent } from './manage-kanji/create-kanji/create-kanji.component';


const routes: Routes = [
  {path: 'admin', component: SidebarComponent,
    children:[
      {path:'list-post', component:ListPostComponent},
      {path: 'lesson', component:ListLessonComponent},
      {path: 'lesson-detail', component:LessonDetailComponent},
      { path:'list-user', component:UserListComponent},
      { path:'list-chat', component:ListChatComponent},
      { path:'list-book', component:ListBookComponent},
      { path:'list-kanji', component:ListKanjiComponent},
      {path:'list-grammar',component:ListGrammarComponent},
      {path:'list-vocabulary',component:VocabularyListComponent},
      {path:'create-kanji', component:CreateKanjiComponent},


  ]},
  

 
  // {path: 'admin/list-post' , component: ListPostComponent},
  
];
@NgModule({
  // declarations: [],
  imports: [RouterModule.forChild(routes)], 
  exports: [RouterModule]
})
export class AdminRoutingModule { }
