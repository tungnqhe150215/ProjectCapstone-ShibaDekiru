import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home.component";
import {HomepageComponent} from "./homepage/homepage.component";
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { HiraganaComponent } from './alphabet/hiragana/hiragana.component';
import { KatakanaComponent } from './alphabet/katakana/katakana.component';
import { LessonModule } from './lesson/lesson.module';



const routes: Routes = [
    {
        path: '', component: HomeComponent,
        children: [
            {path: 'home', component: HomepageComponent},
            {path: '', redirectTo: 'home', pathMatch: 'full'},
            {path: 'hiragana', component: HiraganaComponent},
            {path: 'katakana', component: KatakanaComponent},
            {
              path: 'lesson',
              loadChildren: () => import('./lesson/lesson.module').then(m => m.LessonModule)
            },
        ]
    },

    {path: 'login', component: UserLoginComponent},

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
