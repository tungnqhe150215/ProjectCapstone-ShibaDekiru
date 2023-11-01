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
import {SharedModule} from "../../../shared/shared.module";

@Component({
  selector: 'app-list-exercise',
  templateUrl: './list-exercise.component.html',
  styleUrls: ['./list-exercise.component.css'],
  standalone: true,
    imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatTabsModule, SharedModule],
})
export class ListExerciseComponent implements OnInit{
  displayedColumns: string[] = ['id', 'title','action'];
  dataSource!: MatTableDataSource<Exercise> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  exercise :Exercise[] = [];
  classWork: ClassWork = new ClassWork();
  id!: number;

  constructor(
    private manageExerciseService:LectureManageExerciseService,
    private manageClassWorkService: ClassworkService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getExercise();
    console.log(this.classWork)
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getExercise(){
    this.id = this.route.snapshot.params['id'];
    this.classWork = new ClassWork();
    this.manageClassWorkService.getClassWorkByID(this.id).subscribe(data => {
      this.classWork = data
      console.log(data)
    });
    this.exercise = []
    this.manageExerciseService.getExerciseByClasswork(this.id).subscribe(data => {
      this.exercise = data;
      this.dataSource = new MatTableDataSource(this.exercise);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.exercise)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteExerciseDialog(id:number){
    this.dialog.open(ExerciseDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getExercise());
  }

  openCreateExerciseDialog(id:number){
    this.dialog.open(ExerciseCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getExercise());
  }

  openUpdateExerciseDialog(id:number){
    this.dialog.open(ExerciseUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getExercise());
  }

  getExerciseDetail(id:number){
    this.router.navigate(['lecturer/class/class-work/exercise',id]);
  }
}

@Component({
  selector: 'app-exercise-delete-dialog',
  templateUrl: 'exercise-delete-dialog.html',
  styleUrls: ['./list-exercise.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ExerciseDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ExerciseDeleteDialog>,
    private manageExerciseService:LectureManageExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteExercise(id:number){
    this.manageExerciseService.deleteExercise(id).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Deleted!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-exercise-create-dialog',
  templateUrl: 'exercise-create-dialog.html',
  styleUrls: ['./list-exercise.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ExerciseCreateDialog {

  exercise:Exercise =  new Exercise;

  constructor(
    public dialogRef: MatDialogRef<ExerciseCreateDialog>,
    private manageExerciseService:LectureManageExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createExercise(){
    console.log(this.exercise)
    this.manageExerciseService.createExercise(this.data,this.exercise).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('New exercise part added!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-exercise-update-dialog',
  templateUrl: 'exercise-update-dialog.html',
  styleUrls: ['./list-exercise.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ExerciseUpdateDialog implements OnInit{

  exercise:Exercise =  new Exercise;

  constructor(
    public dialogRef: MatDialogRef<ExerciseUpdateDialog>,
    private manageExerciseService:LectureManageExerciseService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.exercise = new Exercise();
    this.manageExerciseService.getExerciseById(this.data).subscribe(e => {
      this.exercise = e
    })
  }

  updateExercise(){
    console.log(this.exercise)
    this.manageExerciseService.updateExercise(this.data,this.exercise).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('Exercise part updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
