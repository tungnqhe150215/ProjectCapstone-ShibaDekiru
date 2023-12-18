import { LessonModule } from './lesson/lesson.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import { HomeRoutingModule } from './home-routing.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserChangePasswordComponent } from './auth/user-change-password/user-change-password.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { HiraganaComponent } from './alphabet/hiragana/hiragana.component';
import { KatakanaComponent } from './alphabet/katakana/katakana.component';
import { ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import { FormsModule }   from '@angular/forms';
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { UserRegisterComponent } from './auth/user-register/user-register.component';
import { HiraganaDetailComponent } from './alphabet/hiragana/hiragana-detail/hiragana-detail.component';
import { KatakanaDetailComponent } from './alphabet/katakana/katakana-detail/katakana-detail.component';
import { ClassListComponent } from './class/class-list/class-list.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { KanjiComponent } from './list-knowledge/kanji/kanji.component';
import { VocabComponent } from './list-knowledge/vocab/vocab.component';
import { GrammarComponent } from './list-knowledge/grammar/grammar.component';
import { GrammarDetailComponent } from './list-knowledge/grammar/grammar-detail/grammar-detail.component';
import { KanjiDetailComponent } from './list-knowledge/kanji/kanji-detail/kanji-detail.component';
import { VocabDetailComponent } from './list-knowledge/vocab/vocab-detail/vocab-detail.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatInputModule} from "@angular/material/input";
import { ListPostComponent } from './post/list-post/list-post.component';
import { PostDetailComponent } from './post/post-detail/post-detail.component';
import { ListCommentComponent } from './post/post-detail/list-comment/list-comment.component';
import { MatTabsModule } from '@angular/material/tabs';
import {ClassworkComponent} from "./class/class-landing/classwork/classwork.component";
import {ClassLandingComponent} from "./class/class-landing/class-landing.component";
import {ClassBoardComponent} from "./class/class-landing/classwork-result/class-board.component";
import { ClassTestComponent } from './class/class-landing/class-test/class-test.component';
import { StudentTestComponent } from './student-test/student-test.component';
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSelectModule} from "@angular/material/select";
import {MatRadioModule} from "@angular/material/radio";
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { StudentListeningTestComponent } from './student-test/student-listening-test/student-listening-test.component';
import { StudentReadingTestComponent } from './student-test/student-reading-test/student-reading-test.component';
import { StudentGrammarVocabTestComponent } from './student-test/student-grammar-vocab-test/student-grammar-vocab-test.component';
import { TestQuestionComponent } from './student-test/test-question/test-question.component';
import { SectionTitleComponent } from './student-test/section-title/section-title.component';
import {MatButtonModule} from "@angular/material/button";
import { TestResultComponent } from './student-test/test-result/test-result.component';
import { TestLandingComponent } from './student-test/test-landing/test-landing.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { WaitingRegisterComponent } from './auth/waiting-register/waiting-register.component';
import { WaitingForgotPasswordComponent } from './auth/waiting-forgot-password/waiting-forgot-password.component';
import { ActiveAccountComponent } from './auth/active-account/active-account.component';
import { StudentClassworkComponent } from './student-classwork/student-classwork.component';
import { ExerciseFieldComponent } from './student-classwork/exercise-field/exercise-field.component';
import { ClassworkCompleteComponent } from './student-classwork/classwork-complete/classwork-complete.component';
import { ClassTestResultComponent } from './class/class-landing/class-test-result/class-test-result.component';
import { DeleteCommentComponent } from './post/post-detail/list-comment/delete-comment/delete-comment.component';
import { UpdateCommentComponent } from './post/post-detail/list-comment/update-comment/update-comment.component';
import {MatDialogModule} from '@angular/material/dialog';
import { NgxPaginationModule } from 'ngx-pagination';
import { UnthorizationComponent } from './unthorization/unthorization.component';
import {SharedModule} from "../shared/shared.module";
import { NotFoundComponent } from './not-found/not-found.component';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    HomepageComponent,
    UserLoginComponent,
    UserChangePasswordComponent,
    ForgotPasswordComponent,
    HiraganaComponent,
    KatakanaComponent,
    UserRegisterComponent,
    HiraganaDetailComponent,
    KatakanaDetailComponent,
    ClassListComponent,
    KanjiComponent,
    VocabComponent,
    GrammarComponent,
    GrammarDetailComponent,
    KanjiDetailComponent,
    VocabDetailComponent,
    ListPostComponent,
    PostDetailComponent,
    ListCommentComponent,
    ClassworkComponent,
    ClassLandingComponent,
    ClassBoardComponent,
    ClassTestComponent,
    StudentTestComponent,
    UserProfileComponent,
    StudentListeningTestComponent,
    StudentReadingTestComponent,
    StudentGrammarVocabTestComponent,
    TestQuestionComponent,
    SectionTitleComponent,
    TestResultComponent,
    TestLandingComponent,
    ResetPasswordComponent,
    WaitingRegisterComponent,
    WaitingForgotPasswordComponent,
    ActiveAccountComponent,
    StudentClassworkComponent,
    ExerciseFieldComponent,
    ClassworkCompleteComponent,
    ClassTestResultComponent,
    DeleteCommentComponent,
    UpdateCommentComponent,
    UnthorizationComponent,
    NotFoundComponent
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
  ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        HttpClientModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        FormsModule,
        MatCardModule,
        LessonModule,
        FontAwesomeModule,
        MatFormFieldModule,
        MatTableModule,
        MatPaginatorModule,
        MatInputModule,
        MatTabsModule,
        MatListModule,
        MatIconModule,
        MatToolbarModule,
        MatSelectModule,
        MatRadioModule,
        MatButtonModule,
        MatDialogModule,
        NgxPaginationModule,
        SharedModule,
    ]
})
export class HomeModule { }
