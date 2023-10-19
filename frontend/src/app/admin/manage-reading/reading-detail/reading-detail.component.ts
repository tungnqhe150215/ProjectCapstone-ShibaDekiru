import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {Reading} from "../../../core/models/reading";
import {AdminManageReadingService} from "../admin-manage-reading.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {AdminManageReadingQuestionService} from "../admin-manage-reading-question.service";
import {ReadingQuestion} from "../../../core/models/reading-question";
import {FormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-reading-detail',
  templateUrl: './reading-detail.component.html',
  styleUrls: ['./reading-detail.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatCardModule],
})
export class ReadingDetailComponent implements OnInit{
  displayedColumns: string[] = ['id', 'question', 'sample-answer','action'];
  dataSource!: MatTableDataSource<ReadingQuestion> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  readingQuestions :ReadingQuestion[] = [];
  reading:Reading = new Reading();
  id!: number;

  constructor(
    private manageReadingService:AdminManageReadingService,
    private manageReadingQuestionService: AdminManageReadingQuestionService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getReadingQuestion();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getReadingQuestion(){
    this.id = this.route.snapshot.params['id'];
    this.reading = new Reading();
    this.manageReadingService.getReadingById(this.id).subscribe(data => {
      this.reading = data
    });
    this.readingQuestions = []
    this.manageReadingQuestionService.getreadingQuestionByReading(this.id).subscribe(data => {
      this.readingQuestions = data;
      this.dataSource = new MatTableDataSource(this.readingQuestions);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openDeleteReadingQuestionDialog(id:number){
    this.dialog.open(ReadingQuestionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getReadingQuestion());
  }

  openCreateReadingQuestionDialog(id:number){
    this.dialog.open(ReadingQuestionCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getReadingQuestion());
  }

  openUpdateReadingQuestionDialog(id:number){
    this.dialog.open(ReadingQuestionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getReadingQuestion());
  }
}
//
//
// Here is the code for reading-question-delete component
//
@Component({
  selector: 'app-reading-question-delete-dialog',
  templateUrl: 'reading-question-delete-dialog.html',
  styleUrls: ['./reading-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingQuestionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ReadingQuestionDeleteDialog>,
    private manageReadingQuestionService: AdminManageReadingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteReadingQuestion(id:number){
    this.manageReadingQuestionService.deleteReadingQuestion(id).subscribe(data => {
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
// Here is the code for reading-question-create component
//
@Component({
  selector: 'app-reading-question-create-dialog',
  templateUrl: 'reading-question-create-dialog.html',
  styleUrls: ['./reading-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingQuestionCreateDialog {

  readingQuestion:ReadingQuestion =  new ReadingQuestion();

  constructor(
    public dialogRef: MatDialogRef<ReadingQuestionCreateDialog>,
    private manageReadingQuestionService: AdminManageReadingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createReadingQuestion(){
    console.log(this.readingQuestion)
    this.manageReadingQuestionService.createReadingQuestion(this.data,this.readingQuestion).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('New reading question added!!', 'Close', {
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
// Here is the code for reading-question-update component
//
@Component({
  selector: 'app-reading-question-update-dialog',
  templateUrl: 'reading-question-update-dialog.html',
  styleUrls: ['./reading-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingQuestionUpdateDialog implements OnInit{

  readingQuestion:ReadingQuestion =  new ReadingQuestion();

  constructor(
    public dialogRef: MatDialogRef<ReadingQuestionUpdateDialog>,
    private manageReadingQuestionService: AdminManageReadingQuestionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.readingQuestion = new ReadingQuestion();
    this.manageReadingQuestionService.getReadingQuestionById(this.data).subscribe(e => {
      this.readingQuestion = e
    })
  }

  updateReadingQuestion(){
    console.log(this.readingQuestion)
    console.log(this.data)
    this.manageReadingQuestionService.updateReadingQuestion(this.data,this.readingQuestion).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Reading question added!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
