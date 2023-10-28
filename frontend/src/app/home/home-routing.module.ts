import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home.component";
import {HomepageComponent} from "./homepage/homepage.component";
import { UserLoginComponent } from './auth/user-login/user-login.component';

const routes: Routes = [
    {
        path: '', component: HomeComponent, 
        children: [
            {path: 'home', component: HomepageComponent},
            {path: '', redirectTo: 'home', pathMatch: 'full'},
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
