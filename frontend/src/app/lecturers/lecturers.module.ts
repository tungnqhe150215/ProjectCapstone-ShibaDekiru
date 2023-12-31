import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CreatePostComponent} from './lecturers-post/create-post/create-post.component';
import {UpdatePostComponent} from './lecturers-post/update-post/update-post.component';
import {ListPostComponent} from './lecturers-post/list-post/list-post.component';
import {ViewPostComponent} from './lecturers-post/view-post/view-post.component';
import {NavsideComponent} from './navside/navside.component';
import {RouterModule} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule} from '@angular/forms';
import {ClassworkComponent} from './classwork/classwork.component';
import {MatTabsModule} from '@angular/material/tabs';
import {AddClassworkComponent} from './classwork/add-classwork/add-classwork.component';
import {UpdateClassworkComponent} from './classwork/update-classwork/update-classwork.component';
import {DeleteClassworkComponent} from './classwork/delete-classwork/delete-classwork.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import {ClassworkDetailComponent} from './classwork/classwork-detail/classwork-detail.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {
  ExerciseCreateDialog,
  ExerciseDeleteDialog, ExerciseUpdateDialog,
  ListExerciseComponent
} from './exercise/list-exercise/list-exercise.component';
import {
  ExerciseDetailComponent,
  ExerciseQuestionCreateDialog, ExerciseQuestionDeleteDialog, ExerciseQuestionUpdateDialog
} from './exercise/exercise-detail/exercise-detail.component';
import {LClassListComponent} from './class/l-class-list/l-class-list.component';
import {SharedModule} from "../shared/shared.module";
import {LectureListTestComponent} from './test/lecture-list-test/lecture-list-test.component';
import {
  LectureTestDetailComponent,
  QuestionBankCreateDialog, QuestionBankDeleteDialog, QuestionBankUpdateDialog
} from './test/lecture-test-detail/lecture-test-detail.component';
import {ClassMemberComponent} from './class/class-member/class-member.component';
import {TestSectionComponent} from './test/test-section/test-section.component';
import {ListeningSectionComponent} from './test/test-section/listening-section/listening-section.component';
import {ReadingSectionComponent} from './test/test-section/reading-section/reading-section.component';
import {GrammarVocabSectionComponent} from './test/test-section/grammar-vocab-section/grammar-vocab-section.component';
import {
  TestAssignComponent,
  TestAssignCreateDialog, TestAssignDeleteDialog,
  TestAssignUpdateDialog
} from './test/test-assign/test-assign.component';
import { TestResultComponent } from './test/test-result/test-result.component';
import { GradeClassworkComponent } from './classwork/grade-classwork/grade-classwork.component';
import { AnswerFieldComponent } from './classwork/grade-classwork/answer-field/answer-field.component';
import { SubmissionListComponent } from './classwork/submission-list/submission-list.component';
import { DeletelecCommentComponent } from './lecturers-post/deletelec-comment/deletelec-comment.component';
import {MatSelectModule} from "@angular/material/select";
import { DeletePostComponent } from './lecturers-post/delete-post/delete-post.component';


@NgModule({
  declarations: [
    CreatePostComponent,
    UpdatePostComponent,
    ListPostComponent,
    ViewPostComponent,
    NavsideComponent,
    ClassworkComponent,
    AddClassworkComponent,
    UpdateClassworkComponent,
    DeleteClassworkComponent,
    ClassworkDetailComponent,
    ClassMemberComponent,
    TestSectionComponent,
    TestResultComponent,
    GradeClassworkComponent,
    AnswerFieldComponent,
    SubmissionListComponent,
    DeletelecCommentComponent,
    LectureTestDetailComponent,
    QuestionBankCreateDialog,
    QuestionBankUpdateDialog,
    QuestionBankDeleteDialog,
    TestAssignComponent,
    TestAssignCreateDialog,
    TestAssignUpdateDialog,
    TestAssignDeleteDialog,
    ExerciseDetailComponent,
    ExerciseQuestionCreateDialog,
    ExerciseQuestionDeleteDialog,
    ExerciseQuestionUpdateDialog,
    ListExerciseComponent,
    ExerciseCreateDialog,
    ExerciseDeleteDialog,
    ExerciseUpdateDialog,
    DeletePostComponent,
  ],
  exports: [
    ClassworkComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    // LecturersRoutingModule,
    RouterModule,
    MatFormFieldModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    MatIconModule,
    MatInputModule,
    FormsModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    SharedModule,
    LClassListComponent,
    LectureListTestComponent,
    GrammarVocabSectionComponent,
    ReadingSectionComponent,
    ListeningSectionComponent,
    MatOptionModule,
    MatSelectModule,
  ]
})
export class LecturersModule {
}
