import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminModule} from "./admin/admin.module";
import { LecturersModule } from './lecturers/lecturers.module';
import { NavsideComponent } from './lecturers/navside/navside.component';
import { ListPostComponent } from './lecturers/lecturers-post/list-post/list-post.component';


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
      {path: 'list-post' ,component: ListPostComponent}
    ]
  }


];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
