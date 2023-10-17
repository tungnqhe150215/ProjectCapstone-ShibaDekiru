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
import { UpdateLessonComponent } from './manage-lesson/update-lesson/update-lesson.component';
import { CreateLessonComponent } from './manage-lesson/create-lesson/create-lesson.component';
import { PostDetailComponent } from './manage-post/post-detail/post-detail.component';
import { ListKanjiComponent } from './manage-kanji/list-kanji/list-kanji.component';
import { ListHiraganaComponent } from './manage-alphabet/list-hiragana/list-hiragana.component';
import { ListKatakanaComponent } from './manage-alphabet/list-katakana/list-katakana.component';
import {ListReadingComponent} from "./manage-reading/list-reading/list-reading.component";
import {ReadingDetailComponent} from "./manage-reading/reading-detail/reading-detail.component";
import { CreateKanjiComponent } from './manage-kanji/create-kanji/create-kanji.component';
import { UpdateKanjiComponent } from './manage-kanji/update-kanji/update-kanji.component';
import { UserDetailComponent } from './manage-user/user-detail/user-detail.component';
import { CreateUserComponent } from './manage-user/create-user/create-user.component';
import { ListVocabularyComponent } from './manage-vocabulary/list-vocabulary/list-vocabulary.component';
import { CreateVocabularyComponent } from './manage-vocabulary/create-vocabulary/create-vocabulary.component';
import { UpdateVocabularyComponent } from './manage-vocabulary/update-vocabulary/update-vocabulary.component';
import { ListGrammarComponent } from './manage-grammar/list-grammar/list-grammar.component';
import { CreateGrammarComponent } from './manage-grammar/create-grammar/create-grammar.component';
import { UpdateGrammarComponent } from './manage-grammar/update-grammar/update-grammar.component';
import {ListListeningComponent} from "./manage-listening/list-listening/list-listening.component";
import {ListeningDetailComponent} from "./manage-listening/listening-detail/listening-detail.component";
import { UpdateUserComponent } from './manage-user/update-user/update-user.component';



const routes: Routes = [
  {path: 'admin', component: SidebarComponent,
    children:[
      {path: 'lesson', component:ListLessonComponent},
      {path: 'lesson/create-lesson', component:CreateLessonComponent},
      {path: 'lesson/update-lesson/:id', component: UpdateLessonComponent},
      {path: 'lesson/lesson-detail/:id', component:LessonDetailComponent},
      {path:'user-account', component:UserListComponent},
      {path:'user-account/userAccountDetail/:id', component:UserDetailComponent},
      {path:'user-account/create-userAccount', component:CreateUserComponent},
      {path:'user-account/update-userAccount/:id', component:UpdateUserComponent},
      // {path:'user-account/update-userAccount/:id', component:UpdateUserComponent},
      {path:'post', component:ListPostComponent},
      {path:'post/post-detail/:id', component:PostDetailComponent },
      { path:'list-user', component:UserListComponent},
      { path:'list-chat', component:ListChatComponent},
      { path:'list-book', component:ListBookComponent},
      { path:'list-kanji', component:ListKanjiComponent},
      { path:'lesson/:id/writing',component:ListWritingComponent},
      { path:'lesson/writing/:id',component:WritingDetailComponent},
      { path:'lesson/:id/reading',component:ListReadingComponent},
      { path:'lesson/reading/:id',component:ReadingDetailComponent},
      { path:'lesson/:id/listening',component:ListListeningComponent},
      { path:'lesson/listening/:id',component:ListeningDetailComponent},
      {path:'list-hiragana',component:ListHiraganaComponent},
      {path:'list-katakana',component:ListKatakanaComponent},
      {path:'create-kanji',component:CreateKanjiComponent},
      {path:'update-kanji/:id',component:UpdateKanjiComponent},
      {path:'list-vocabulary',component:ListVocabularyComponent},
      {path:'create-vocabulary',component:CreateVocabularyComponent},
      {path:'update-vocabulary/:id',component:UpdateVocabularyComponent},
      {path:'list-grammar',component:ListGrammarComponent},
      {path:'create-grammar',component:CreateGrammarComponent},
      {path:'update-grammar/:id',component:UpdateGrammarComponent},
  ]},



  // {path: 'admin/list-post' , component: ListPostComponent},

];
@NgModule({
  // declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
