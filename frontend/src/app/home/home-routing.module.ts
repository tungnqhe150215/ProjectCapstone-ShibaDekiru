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
      // {path: 'book/:id/lesson/detail/:id', component: DetailComponent},
      // {
      //   path: 'book',
      //   loadChildren: () => import('./lesson/lesson.module').then(m => m.LessonModule)
      // },
      {path: 'post', component: ListPostComponent },
      {path: 'post/post-detail/:id', component:PostDetailComponent},
    ]
  },

  {path: 'login', component: UserLoginComponent},
  {path: 'register', component: UserRegisterComponent},
    
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {
}
