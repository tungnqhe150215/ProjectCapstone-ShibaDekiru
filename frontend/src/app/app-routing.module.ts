import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminModule} from "./admin/admin.module";
import { LecturersModule } from './lecturers/lecturers.module';
import { NavsideComponent } from './lecturers/navside/navside.component';
import { ListPostComponent } from './lecturers/lecturers-post/list-post/list-post.component';
import { ViewPostComponent } from './lecturers/lecturers-post/view-post/view-post.component';
import { CreatePostComponent } from './lecturers/lecturers-post/create-post/create-post.component';
import { UpdatePostComponent } from './lecturers/lecturers-post/update-post/update-post.component';


const routes: Routes = [
  {
    path: 'admin',
    component: AdminModule,
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  },
  // {
  //   path: 'lecturers',
  //   component: LecturersModule,
  //   loadChildren: () => import('./lecturers/lecturers.module').then(m => m.LecturersModule)
  // },
  {
    path: 'lecturers',
    component: NavsideComponent,
    children:[
      {path: 'post' ,component: ListPostComponent},
      {path: '', redirectTo: 'post', pathMatch: 'full'},
      {path: 'post/post-detail/:id', component:ViewPostComponent},
      {path: 'post/create-post', component:CreatePostComponent},
      {path: 'post/update-post/:id', component:UpdatePostComponent}
    ]
  }


];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
