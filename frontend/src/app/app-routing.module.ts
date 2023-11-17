import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminModule} from "./admin/admin.module";
import { LecturersModule } from './lecturers/lecturers.module';
import { NavsideComponent } from './lecturers/navside/navside.component';
import { ListPostComponent } from './lecturers/lecturers-post/list-post/list-post.component';
import { ViewPostComponent } from './lecturers/lecturers-post/view-post/view-post.component';
import { CreatePostComponent } from './lecturers/lecturers-post/create-post/create-post.component';
import { UpdatePostComponent } from './lecturers/lecturers-post/update-post/update-post.component';
import { ClassworkComponent } from './lecturers/classwork/classwork.component';
import { AddClassworkComponent } from './lecturers/classwork/add-classwork/add-classwork.component';
import { UpdateClassworkComponent } from './lecturers/classwork/update-classwork/update-classwork.component';
import { HomeModule } from './home/home.module';
import {ExerciseDetailComponent} from "./lecturers/exercise/exercise-detail/exercise-detail.component";
import {ListExerciseComponent} from "./lecturers/exercise/list-exercise/list-exercise.component";
import {LClassListComponent} from "./lecturers/class/l-class-list/l-class-list.component";
import {LectureListTestComponent} from "./lecturers/test/lecture-list-test/lecture-list-test.component";
import {LectureTestDetailComponent} from "./lecturers/test/lecture-test-detail/lecture-test-detail.component";
import {TestSectionComponent} from "./lecturers/test/test-section/test-section.component";
import {TestAssignComponent} from "./lecturers/test/test-assign/test-assign.component";
import {TestResultComponent} from "./lecturers/test/test-result/test-result.component";
import {GradeClassworkComponent} from "./lecturers/classwork/grade-classwork/grade-classwork.component";
import {SubmissionListComponent} from "./lecturers/classwork/submission-list/submission-list.component";


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
    path: 'lecturer',
    component: NavsideComponent,
    children:[
      // {path: 'post' ,component: ListPostComponent},
      // {path: '', redirectTo: 'post', pathMatch: 'full'},
      {path: 'post/post-detail/:id', component:ViewPostComponent},
      // {path: ':id/post', component:CreatePostComponent},
      {path: 'post/update-post/:id', component:UpdatePostComponent},
      {path: 'class-work', component: ClassworkComponent},
      {path: ':id/post' ,component: ListPostComponent},
      // {path: 'class',component:ClassworkComponent},
      {path: 'class/:id/class-work', component:ClassworkComponent},
      {path: 'class/class-work/:id', component:UpdateClassworkComponent},
      {path: 'class/:classId/cw/:classWorkId/s', component: SubmissionListComponent},
      {path: 'class/:classId/cw/:classworkId/s/:studentId', component:GradeClassworkComponent},
      {path: 'class/class-work/:id/exercise', component:ListExerciseComponent},
      {path: 'class/class-work/exercise/:id', component:ExerciseDetailComponent},
      {path: 'class', component:LClassListComponent},
      {path: 'test',component:LectureListTestComponent},
      {path: 'test/:id',component:TestSectionComponent},
      {path: 'test/section/:id',component:LectureTestDetailComponent},
      {path: 'test/:id/assign',component:TestAssignComponent},
      {path: 'test/:id/result',component:TestResultComponent},
      // {path: 'class/:id/create-work', component:AddClassworkComponent},
    ]
  },
  // {
  //   path: 'home',
  //   component: HomeModule,
  //   loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
  // }



];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
