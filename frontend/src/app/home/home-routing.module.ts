import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home.component";
import {HomepageComponent} from "./homepage/homepage.component";
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { HiraganaComponent } from './alphabet/hiragana/hiragana.component';
import { KatakanaComponent } from './alphabet/katakana/katakana.component';
import { VocabComponent } from './list-knowledge/vocab/vocab.component';
import { GrammarComponent } from './list-knowledge/grammar/grammar.component';
import { KanjiComponent } from './list-knowledge/kanji/kanji.component';



const routes: Routes = [
    {
        path: '', component: HomeComponent, 
        children: [
            {path: 'home', component: HomepageComponent},
            {path: '', redirectTo: 'home', pathMatch: 'full'},
            {path: 'hiragana', component: HiraganaComponent},
            {path: 'katakana', component: KatakanaComponent},
            {path: 'vocabulary', component: VocabComponent},
            {path: 'grammar', component: GrammarComponent},
            {path: 'kanji', component: KanjiComponent},
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
