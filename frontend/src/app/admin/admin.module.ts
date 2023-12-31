import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { BanUserComponent } from './manage-user/ban-user/ban-user.component';
import { CreateUserComponent } from './manage-user/create-user/create-user.component';
import { UserDetailComponent } from './manage-user/user-detail/user-detail.component';
import { UserListComponent } from './manage-user/user-list/user-list.component';
import { DeleteChatComponent } from './manage-chat/delete-chat/delete-chat.component';
import { ListChatComponent } from './manage-chat/list-chat/list-chat.component';
import { ChatConversationComponent } from './manage-chat/chat-conversation/chat-conversation.component';
import { ListPostComponent } from './manage-post/list-post/list-post.component';
import { PostDetailComponent } from './manage-post/post-detail/post-detail.component';
import { ListLessonComponent } from './manage-lesson/list-lesson/list-lesson.component';
import { LessonDetailComponent } from './manage-lesson/lesson-detail/lesson-detail.component';
import { AdminRoutingModule } from './admin-routing.module';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { CreateBookComponent } from './manage-book/create-book/create-book.component';
import { UpdateBookComponent } from './manage-book/update-book/update-book.component';
import { ListBookComponent } from './manage-book/list-book/list-book.component';

import { ListWritingComponent } from './manage-writing/list-writing/list-writing.component';
import { WritingDetailComponent } from './manage-writing/writing-detail/writing-detail.component';
import { ListWritingQuestionComponent } from './manage-writing/list-writing-question/list-writing-question.component';
import { WritingQuestionDetailComponent } from './manage-writing/writing-question-detail/writing-question-detail.component';
import {MatTableModule} from '@angular/material/table';
import { HttpClientModule } from '@angular/common/http';
import {MatPaginatorModule} from '@angular/material/paginator';
import { CreateLessonComponent } from './manage-lesson/create-lesson/create-lesson.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from "@angular/material/input";
import {MatSortModule} from '@angular/material/sort';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatFormFieldModule} from '@angular/material/form-field';
// import {
//   MatSnackBar,
//   MatSnackBarHorizontalPosition,
//   MatSnackBarVerticalPosition,
// } from '@angular/material/snack-bar';
import { UpdateLessonComponent } from './manage-lesson/update-lesson/update-lesson.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
// import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
// import {MatSortModule} from "@angular/material/sort";
import { ListKanjiComponent } from './manage-kanji/list-kanji/list-kanji.component';
import { ListHiraganaComponent } from './manage-alphabet/list-hiragana/list-hiragana.component';
import { ListKatakanaComponent } from './manage-alphabet/list-katakana/list-katakana.component';
import { ListReadingComponent } from './manage-reading/list-reading/list-reading.component';
import { ReadingDetailComponent } from './manage-reading/reading-detail/reading-detail.component';
import { CreateKanjiComponent } from './manage-kanji/create-kanji/create-kanji.component';
import { UpdateKanjiComponent } from './manage-kanji/update-kanji/update-kanji.component';
import { ListVocabularyComponent } from './manage-vocabulary/list-vocabulary/list-vocabulary.component';
import { CreateVocabularyComponent } from './manage-vocabulary/create-vocabulary/create-vocabulary.component';
import { UpdateVocabularyComponent } from './manage-vocabulary/update-vocabulary/update-vocabulary.component';
import { ListGrammarComponent } from './manage-grammar/list-grammar/list-grammar.component';
import { CreateGrammarComponent } from './manage-grammar/create-grammar/create-grammar.component';
import { UpdateGrammarComponent } from './manage-grammar/update-grammar/update-grammar.component';
import { ListListeningComponent } from './manage-listening/list-listening/list-listening.component';
import { ListeningDetailComponent } from './manage-listening/listening-detail/listening-detail.component';
import { UpdateUserComponent } from './manage-user/update-user/update-user.component';
import {MatSelectModule} from '@angular/material/select';
import { CreateHiraganaComponent } from './manage-alphabet/create-hiragana/create-hiragana.component';
import { UpdateHiraganaComponent } from './manage-alphabet/update-hiragana/update-hiragana.component';
import { CreateKatakanaComponent } from './manage-alphabet/create-katakana/create-katakana.component';
import { UpdateKatakanaComponent } from './manage-alphabet/update-katakana/update-katakana.component';
import { ListClassComponent } from './manage-class/list-class/list-class.component';
import { ClassDetailComponent } from './manage-class/class-detail/class-detail.component';
import { ListKaiwaComponent } from './manage-kaiwa/list-kaiwa/list-kaiwa.component';
import { BookDetailComponent } from './manage-book/book-detail/book-detail.component';
import { ListAllLessonComponent } from './manage-lesson/list-all-lesson/list-all-lesson.component';
import { DeleteLessonComponent } from './manage-lesson/delete-lesson/delete-lesson.component';
import { DeleteBookComponent } from './manage-book/delete-book/delete-book.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { SharedModule } from '../shared/shared.module';
import { DeleteKanjiComponent } from './manage-kanji/delete-kanji/delete-kanji.component';
import { DeleteGrammarComponent } from './manage-grammar/delete-grammar/delete-grammar.component';
import { DeleteVocabularyComponent } from './manage-vocabulary/delete-vocabulary/delete-vocabulary.component';
import { DeleteHiraganaComponent } from './manage-alphabet/delete-hiragana/delete-hiragana.component';
import { DeleteKatakanaComponent } from './manage-alphabet/delete-katakana/delete-katakana.component';


@NgModule({
  declarations: [
    SidebarComponent,
    BanUserComponent,
    UserDetailComponent,
    UserListComponent,
    DeleteChatComponent,
    ListChatComponent,
    ChatConversationComponent,
    ListPostComponent,
    PostDetailComponent,
    ListLessonComponent,
    LessonDetailComponent,
    CreateBookComponent,
    UpdateBookComponent,
    ListBookComponent,
    ListWritingQuestionComponent,
    WritingQuestionDetailComponent,
    CreateLessonComponent,
    UpdateLessonComponent,
    ListKanjiComponent,
    ListHiraganaComponent,
    CreateHiraganaComponent,
    UpdateHiraganaComponent,
    ListKatakanaComponent,
    CreateKanjiComponent,
    UpdateKanjiComponent,
    ListVocabularyComponent,
    CreateVocabularyComponent,
    UpdateVocabularyComponent,
    ListGrammarComponent,
    CreateGrammarComponent,
    UpdateGrammarComponent,
    UpdateUserComponent,
    CreateKatakanaComponent,
    UpdateKatakanaComponent,
    ClassDetailComponent,
    BookDetailComponent,
    ListAllLessonComponent,
    DeleteLessonComponent,
    DeleteBookComponent,
    DeleteKanjiComponent,
    DeleteGrammarComponent,
    DeleteVocabularyComponent,
    DeleteHiraganaComponent,
    DeleteKatakanaComponent,


  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatGridListModule,
    MatMenuModule,
    MatTableModule,
    HttpClientModule,
    MatPaginatorModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatSortModule,
    //Standalone component
    ListWritingComponent,
    WritingDetailComponent,
    ListReadingComponent,
    ReadingDetailComponent,
    ListListeningComponent,
    ListeningDetailComponent,
    ListClassComponent,
    ListKaiwaComponent,
    MatSnackBarModule,
    MatFormFieldModule,
    // MatSortModule,
    ReactiveFormsModule,
    MatInputModule,
    MatRadioModule,
    MatSortModule,
    CreateUserComponent,
    MatSelectModule,
    NgxPaginationModule,
    SharedModule,
  ]
})
export class AdminModule { }
