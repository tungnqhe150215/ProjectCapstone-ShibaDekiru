import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { LessonComponent } from './lesson.component';
import { ListComponent } from './list/list.component';
import { DetailComponent } from './detail/detail.component';
import { SectionComponent } from './section/section.component';

const routes: Routes = [
  // {
  //   path: '', component: LessonComponent,
  //   children: [
  //     {path: '', component: ListComponent},
  //     {path: 'detail/:id', component: DetailComponent},
  //     {path: 'detail/:id/section', component: SectionComponent}
  //   ]
  // },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LessonRoutingModule {
}
