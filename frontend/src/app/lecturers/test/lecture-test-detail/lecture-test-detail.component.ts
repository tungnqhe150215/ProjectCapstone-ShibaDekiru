import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {Test} from "../../../core/models/test";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {QuestionBank} from "../../../core/models/question-bank";
import {FormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatSnackBar} from "@angular/material/snack-bar";
import {LectureManageQuestionBankService} from "../lecture-manage-question-bank.service";
import {LectureManageTestService} from "../lecture-manage-test.service";
import {SharedModule} from "../../../shared/shared.module";

@Component({
  selector: 'app-lecture-test-detail',
  templateUrl: './lecture-test-detail.component.html',
  styleUrls: ['./lecture-test-detail.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatCardModule, SharedModule],
})
export class LectureTestDetailComponent implements OnInit{
  displayedColumns: string[] = ['id', 'question', 'answer','action'];
  dataSource!: MatTableDataSource<QuestionBank> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  questionBanks :QuestionBank[] = [];
  test:Test = new Test();
  id!: number;

  constructor(
    private manageTestService:LectureManageTestService,
    private manageQuestionBankService: LectureManageQuestionBankService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getQuestionBank();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getQuestionBank(){
    this.id = this.route.snapshot.params['id'];
    this.test = new Test();
    this.manageTestService.getTestById(this.id).subscribe(data => {
      this.test = data
      console.log(this.test)
    });
    this.questionBanks = []
    this.manageQuestionBankService.getquestionBankByTest(this.id).subscribe(data => {
      this.questionBanks = data;
      this.dataSource = new MatTableDataSource(this.questionBanks);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openDeleteQuestionBankDialog(id:number){
    this.dialog.open(QuestionBankDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getQuestionBank());
  }

  openCreateQuestionBankDialog(id:number){
    this.dialog.open(QuestionBankCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getQuestionBank());
  }

  openUpdateQuestionBankDialog(id:number){
    this.dialog.open(QuestionBankUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getQuestionBank());
  }
}
//
//
// Here is the code for test-question-delete component
//
@Component({
  selector: 'app-test-question-delete-dialog',
  templateUrl: 'question-bank-delete-dialog.html',
  styleUrls: ['./lecture-test-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class QuestionBankDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<QuestionBankDeleteDialog>,
    private manageQuestionBankService: LectureManageQuestionBankService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteQuestionBank(id:number){
    this.manageQuestionBankService.deleteQuestionBank(id).subscribe(data => {
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
// Here is the code for test-question-create component
//
@Component({
  selector: 'app-test-question-create-dialog',
  templateUrl: 'question-bank-create-dialog.html',
  styleUrls: ['./lecture-test-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class QuestionBankCreateDialog {

  questionBank:QuestionBank =  new QuestionBank();

  constructor(
    public dialogRef: MatDialogRef<QuestionBankCreateDialog>,
    private manageQuestionBankService: LectureManageQuestionBankService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createQuestionBank(){
    console.log(this.questionBank)
    this.manageQuestionBankService.createQuestionBank(this.data,this.questionBank).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Test question added!!', 'Close', {
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
// Here is the code for test-question-update component
//
@Component({
  selector: 'app-test-question-update-dialog',
  templateUrl: 'question-bank-update-dialog.html',
  styleUrls: ['./lecture-test-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class QuestionBankUpdateDialog implements OnInit {

  questionBank: QuestionBank = new QuestionBank();

  constructor(
    public dialogRef: MatDialogRef<QuestionBankUpdateDialog>,
    private manageQuestionBankService: LectureManageQuestionBankService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {
  }

  ngOnInit(): void {
    this.questionBank = new QuestionBank();
    this.manageQuestionBankService.getQuestionBankById(this.data).subscribe(e => {
      this.questionBank = e
    })
  }

  updateQuestionBank() {
    console.log(this.questionBank)
    console.log(this.data)
    this.manageQuestionBankService.updateQuestionBank(this.data, this.questionBank).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Test question updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

