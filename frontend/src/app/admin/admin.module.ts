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
import { DeleteBookComponent } from './manage-book/delete-book/delete-book.component';
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
import { CreateKanjiComponent } from './manage-kanji/create-kanji/create-kanji.component';

@NgModule({
  declarations: [
    SidebarComponent,
    BanUserComponent,
    CreateUserComponent,
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
    DeleteBookComponent,
    ListWritingQuestionComponent,
    WritingQuestionDetailComponent,
    CreateLessonComponent,
    UpdateLessonComponent,
    ListKanjiComponent,
    ListHiraganaComponent,
    ListKatakanaComponent,
    CreateKanjiComponent,
    

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
    MatSnackBarModule,
    MatFormFieldModule,
    // MatSortModule,
    ReactiveFormsModule,
    MatInputModule,
    MatRadioModule,
    MatSortModule,

  ]
})
export class AdminModule { }
