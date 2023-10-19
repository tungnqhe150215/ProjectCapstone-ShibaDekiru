import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {Writing} from "../../../core/models/writing";
import {Lesson} from "../../../core/models/lesson";
import {AdminManageWritingService} from "../admin-manage-writing.service";
import {LessonService} from "../../../core/services/lesson.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {WritingCreateDialog, WritingDeleteDialog, WritingUpdateDialog} from "../list-writing/list-writing.component";
import {AdminManageWritingQuestionService} from "../admin-manage-writing-question.service";
import {WritingQuestion} from "../../../core/models/writing-question";
import {FormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-writing-detail',
  templateUrl: './writing-detail.component.html',
  styleUrls: ['./writing-detail.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatCardModule],
})
export class WritingDetailComponent implements OnInit{
  displayedColumns: string[] = ['id', 'question', 'sample-answer','action'];
  dataSource!: MatTableDataSource<WritingQuestion> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  writingQuestions :WritingQuestion[] = [];
  writing:Writing = new Writing();
  id!: number;

  constructor(
    private manageWritingService:AdminManageWritingService,
    private manageWritingQuestionService: AdminManageWritingQuestionService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getWritingQuestion();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getWritingQuestion(){
    this.id = this.route.snapshot.params['id'];
    this.writing = new Writing();
    this.manageWritingService.getWritingById(this.id).subscribe(data => {
      this.writing = data
    });
    this.writingQuestions = []
    this.manageWritingQuestionService.getwritingQuestionByWriting(this.id).subscribe(data => {
      this.writingQuestions = data;
      this.dataSource = new MatTableDataSource(this.writingQuestions);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openDeleteWritingQuestionDialog(id:number){
    this.dialog.open(WritingQuestionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getWritingQuestion());
  }

  openCreateWritingQuestionDialog(id:number){
    this.dialog.open(WritingQuestionCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getWritingQuestion());
  }

  openUpdateWritingQuestionDialog(id:number){
    this.dialog.open(WritingQuestionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getWritingQuestion());
  }
}
//
//
// Here is the code for writing-question-delete component
//
@Component({
  selector: 'app-writing-question-delete-dialog',
  templateUrl: 'writing-question-delete-dialog.html',
  styleUrls: ['./writing-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingQuestionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<WritingQuestionDeleteDialog>,
    private manageWritingQuestionService: AdminManageWritingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteWritingQuestion(id:number){
    this.manageWritingQuestionService.deleteWritingQuestion(id).subscribe(data => {
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
//
//
// Here is the code for writing-question-create component
//
@Component({
  selector: 'app-writing-question-create-dialog',
  templateUrl: 'writing-question-create-dialog.html',
  styleUrls: ['./writing-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingQuestionCreateDialog {

  writingQuestion:WritingQuestion =  new WritingQuestion();

  constructor(
    public dialogRef: MatDialogRef<WritingQuestionCreateDialog>,
    private manageWritingQuestionService: AdminManageWritingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createWritingQuestion(){
    console.log(this.writingQuestion)
    this.manageWritingQuestionService.createWritingQuestion(this.data,this.writingQuestion).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('New writing question created!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
//
//
// Here is the code for writing-question-update component
//
@Component({
  selector: 'app-writing-question-update-dialog',
  templateUrl: 'writing-question-update-dialog.html',
  styleUrls: ['./writing-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingQuestionUpdateDialog implements OnInit{

  writingQuestion:WritingQuestion =  new WritingQuestion();

  constructor(
    public dialogRef: MatDialogRef<WritingQuestionUpdateDialog>,
    private manageWritingQuestionService: AdminManageWritingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.writingQuestion = new WritingQuestion();
    this.manageWritingQuestionService.getWritingQuestionById(this.data).subscribe(e => {
      this.writingQuestion = e
    })
  }

  updateWritingQuestion(){
    console.log(this.writingQuestion)
    console.log(this.data)
    this.manageWritingQuestionService.updateWritingQuestion(this.data,this.writingQuestion).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Writing question updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
