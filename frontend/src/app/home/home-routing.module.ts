import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home.component";
import {HomepageComponent} from "./homepage/homepage.component";
import { UserRegisterComponent } from './auth/user-register/user-register.component';
import {UserLoginComponent} from './auth/user-login/user-login.component';
import {HiraganaComponent} from './alphabet/hiragana/hiragana.component';
import {KatakanaComponent} from './alphabet/katakana/katakana.component';
import {ClassListComponent} from "./class/class-list/class-list.component";
import { VocabComponent } from './list-knowledge/vocab/vocab.component';
import { GrammarComponent } from './list-knowledge/grammar/grammar.component';
import { KanjiComponent } from './list-knowledge/kanji/kanji.component';
import { LessonModule } from './lesson/lesson.module';
import {ClassLandingComponent} from "./class/class-landing/class-landing.component";
import { PostDetailComponent } from './post/post-detail/post-detail.component';
import { ListPostComponent } from './post/list-post/list-post.component';
import { LessonComponent } from './lesson/lesson.component';
import { ListLessonComponent } from '../admin/manage-lesson/list-lesson/list-lesson.component';
import { ListComponent } from './lesson/list/list.component';
import { DetailComponent } from './lesson/detail/detail.component';
import { ListVocablessComponent } from './lesson/list-vocabless/list-vocabless.component';
import { ListKaiwaComponent } from './lesson/list-kaiwa/list-kaiwa.component';
import { ListGrammarlessComponent } from './lesson/list-grammarless/list-grammarless.component';
import { ListReadingComponent } from './lesson/list-reading/list-reading.component';
import { ListWritingComponent } from './lesson/list-writing/list-writing.component';
import { ListListeningComponent } from './lesson/list-listening/list-listening.component';
import { ListKanjilessComponent } from './lesson/list-kanjiless/list-kanjiless.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { UserChangePasswordComponent } from './auth/user-change-password/user-change-password.component';


const routes: Routes = [
  {
    path: '', component: HomeComponent,
    children: [
      {path: 'home', component: HomepageComponent},
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'hiragana', component: HiraganaComponent},
      {path: 'katakana', component: KatakanaComponent},
      {path: 'class', component: ClassListComponent},
      {path: 'class/:id', component: ClassLandingComponent},
      {path: 'vocabulary', component: VocabComponent},
      {path: 'grammar', component: GrammarComponent},
      {path: 'kanji', component: KanjiComponent},
      {path: 'book/:id/lesson',component: ListComponent},
      {path: 'lesson/:id/detail', component:DetailComponent},
      {path: 'lesson/:id/vocabulary', component:ListVocablessComponent},
      {path: 'lesson/:id/kanji', component:ListKanjilessComponent},
      {path: 'lesson/:id/kaiwa', component:ListKaiwaComponent},
      {path: 'lesson/:id/grammar', component:ListGrammarlessComponent},
      {path: 'lesson/:id/reading', component: ListReadingComponent},
      {path: 'lesson/:id/writing', component:ListWritingComponent},
      {path: 'lesson/:id/listening', component:ListListeningComponent},
      // {path: 'book/:id/lesson/detail/:id', component: DetailComponent},
      // {
      //   path: 'book',
      //   loadChildren: () => import('./lesson/lesson.module').then(m => m.LessonModule)
      // },
      {path: 'post', component: ListPostComponent },
      {path: 'post/post-detail/:id', component:PostDetailComponent},
    ]
  },
  {path: 'user-profile', component:UserProfileComponent},
  {path: 'login', component: UserLoginComponent},
  {path: 'register', component: UserRegisterComponent},
  {path: 'reset-password', component:ForgotPasswordComponent},
  {path: 'change-password', component:UserChangePasswordComponent},
    
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {
}
