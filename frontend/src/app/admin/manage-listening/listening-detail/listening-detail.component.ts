import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {Listening} from "../../../core/models/listening";
import {AdminManageListeningService} from "../admin-manage-listening.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {AdminManageListeningQuestionService} from "../admin-manage-listening-question.service";
import {ListeningQuestion} from "../../../core/models/listening-question";
import {FormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-listening-detail',
  templateUrl: './listening-detail.component.html',
  styleUrls: ['./listening-detail.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatCardModule],
})
export class ListeningDetailComponent implements OnInit{
  displayedColumns: string[] = ['id', 'question', 'correct-answer','action'];
  dataSource!: MatTableDataSource<ListeningQuestion> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  listeningQuestions :ListeningQuestion[] = [];
  listening:Listening = new Listening();
  id!: number;

  constructor(
      private manageListeningService:AdminManageListeningService,
      private manageListeningQuestionService: AdminManageListeningQuestionService,
      private router:Router,
      private route:ActivatedRoute,
      public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getListeningQuestion();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getListeningQuestion(){
    this.id = this.route.snapshot.params['id'];
    this.listening = new Listening();
    this.manageListeningService.getListeningById(this.id).subscribe(data => {
      this.listening = data
    });
    this.listeningQuestions = []
    this.manageListeningQuestionService.getlisteningQuestionByListening(this.id).subscribe(data => {
      this.listeningQuestions = data;
      this.dataSource = new MatTableDataSource(this.listeningQuestions);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openDeleteListeningQuestionDialog(id:number){
    this.dialog.open(ListeningQuestionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getListeningQuestion());
  }

  openCreateListeningQuestionDialog(id:number){
    this.dialog.open(ListeningQuestionCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getListeningQuestion());
  }

  openUpdateListeningQuestionDialog(id:number){
    this.dialog.open(ListeningQuestionUpdateDialog,
        {
          data: id
        }
    ).afterClosed().subscribe(() => this.getListeningQuestion());
  }
}
//
//
// Here is the code for listening-question-delete component
//
@Component({
  selector: 'app-listening-question-delete-dialog',
  templateUrl: 'listening-question-delete-dialog.html',
  styleUrls: ['./listening-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningQuestionDeleteDialog {
  constructor(
      public dialogRef: MatDialogRef<ListeningQuestionDeleteDialog>,
      private manageListeningQuestionService: AdminManageListeningQuestionService,
      private _snackBar: MatSnackBar,
      @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteListeningQuestion(id:number){
    this.manageListeningQuestionService.deleteListeningQuestion(id).subscribe(data => {
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
// Here is the code for listening-question-create component
//
@Component({
  selector: 'app-listening-question-create-dialog',
  templateUrl: 'listening-question-create-dialog.html',
  styleUrls: ['./listening-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningQuestionCreateDialog {

  listeningQuestion:ListeningQuestion =  new ListeningQuestion();

  constructor(
      public dialogRef: MatDialogRef<ListeningQuestionCreateDialog>,
      private manageListeningQuestionService: AdminManageListeningQuestionService,
      private _snackBar: MatSnackBar,
      @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createListeningQuestion(){
    console.log(this.listeningQuestion)
    this.manageListeningQuestionService.createListeningQuestion(this.data,this.listeningQuestion).subscribe(data => {
      this.dialogRef.close();
    })
      this._snackBar.open('Listening question added!!', 'Close', {
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
// Here is the code for listening-question-update component
//
@Component({
  selector: 'app-listening-question-update-dialog',
  templateUrl: 'listening-question-update-dialog.html',
  styleUrls: ['./listening-detail.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningQuestionUpdateDialog implements OnInit {

  listeningQuestion: ListeningQuestion = new ListeningQuestion();

  constructor(
      public dialogRef: MatDialogRef<ListeningQuestionUpdateDialog>,
      private manageListeningQuestionService: AdminManageListeningQuestionService,
      private _snackBar: MatSnackBar,
      @Inject(MAT_DIALOG_DATA) public data: number,
  ) {
  }

  ngOnInit(): void {
    this.listeningQuestion = new ListeningQuestion();
    this.manageListeningQuestionService.getListeningQuestionById(this.data).subscribe(e => {
      this.listeningQuestion = e
    })
  }

  updateListeningQuestion() {
    console.log(this.listeningQuestion)
    console.log(this.data)
    this.manageListeningQuestionService.updateListeningQuestion(this.data, this.listeningQuestion).subscribe(data => {
      this.dialogRef.close();
    })
      this._snackBar.open('Listening question updated!!', 'Close', {
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
