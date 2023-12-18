import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Exercise} from "../../../core/models/exercise";
import {LectureManageExerciseService} from "../lecture-manage-exercise.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {data} from "autoprefixer";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ClassworkService} from "../../classwork/classwork.service";
import {ClassWork} from "../../../core/models/class-work";
import {MatTabsModule} from "@angular/material/tabs";
import {WritingExercise} from "../../../core/models/writing-exercise";
import {LecturerManageWritingExerciseService} from "../lecturer-manage-writing-exercise.service";
import {SharedModule} from "../../../shared/shared.module";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-exercise-detail',
  templateUrl: './exercise-detail.component.html',
  styleUrls: ['./exercise-detail.component.css'],
})
export class ExerciseDetailComponent implements OnInit{
  displayedColumns: string[] = ['id', 'question','mark','action'];
  dataSource!: MatTableDataSource<WritingExercise> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  writingExercise :WritingExercise[] = [];
  exercise: Exercise = new Exercise();
  id!: number;

  constructor(
    private manageExerciseService:LectureManageExerciseService,
    private manageWritingExerciseService: LecturerManageWritingExerciseService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getWritingExercise();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getWritingExercise(){
    this.id = this.route.snapshot.params['id'];
    this.exercise = new Exercise();
    this.manageExerciseService.getExerciseById(this.id).subscribe(data => {
      this.exercise = data
      console.log(data)
    });
    this.writingExercise = []
    this.manageWritingExerciseService.getWritingExerciseByExercise(this.id).subscribe(data => {
      this.writingExercise = data;
      this.dataSource = new MatTableDataSource(this.writingExercise);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    // return this.courseService.getCourseList();
  }

  openDeleteExerciseQuestionDialog(id:number){
    this.dialog.open(ExerciseQuestionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(data => { if (data)this.getWritingExercise() });
  }

  openCreateExerciseQuestionDialog(id:number){
    this.dialog.open(ExerciseQuestionCreateDialog,{
      data: id
    }).afterClosed().subscribe(data => { if (data)this.getWritingExercise() });
  }

  openUpdateExerciseQuestionDialog(id:number){
    this.dialog.open(ExerciseQuestionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(data => { if (data)this.getWritingExercise() });
  }
}

@Component({
  selector: 'lecturer-exercise-question-delete-dialog',
  templateUrl: 'exercise-question-delete-dialog.html',
  styleUrls: ['./exercise-detail.component.css'],
})
export class ExerciseQuestionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ExerciseQuestionDeleteDialog>,
    private manageWritingExerciseService:LecturerManageWritingExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteWritingExercise(id:number){
    this.manageWritingExerciseService.deleteWritingExercise(id).subscribe(data => {
      this.dialogRef.close(data);
    })
    this._snackBar.open('Đã xóa câu hỏi!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'lecturer-exercise-question-create-dialog',
  templateUrl: 'exercise-question-create-dialog.html',
  styleUrls: ['./exercise-detail.component.css'],
})
export class ExerciseQuestionCreateDialog {

  writingExercise:WritingExercise =  new WritingExercise();

  constructor(
    public dialogRef: MatDialogRef<ExerciseQuestionCreateDialog>,
    private manageWritingExerciseService:LecturerManageWritingExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createWritingExercise(){
    console.log(this.writingExercise)
    this.manageWritingExerciseService.createWritingExercise(this.data,this.writingExercise).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Câu hỏi mới đã được thêm!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'lecturer-exercise-question-update-dialog',
  templateUrl: 'exercise-question-update-dialog.html',
  styleUrls: ['./exercise-detail.component.css'],
})
export class ExerciseQuestionUpdateDialog implements OnInit{

  writingExercise:WritingExercise =  new WritingExercise();

  constructor(
    public dialogRef: MatDialogRef<ExerciseQuestionUpdateDialog>,
    private manageWritingExerciseService:LecturerManageWritingExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.writingExercise = new WritingExercise();
    this.manageWritingExerciseService.getWritingExerciseById(this.data).subscribe(e => {
      this.writingExercise = e
    })
  }

  updateWritingExercise(){
    console.log(this.writingExercise)
    this.manageWritingExerciseService.updateWritingExercise(this.data,this.writingExercise).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Câu hỏi đã được cập nhật!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
