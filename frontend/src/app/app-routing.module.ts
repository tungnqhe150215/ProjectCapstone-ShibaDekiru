import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminModule} from "./admin/admin.module";


const routes: Routes = [
  {
    path: 'admin',
    component: AdminModule,
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  },


];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
