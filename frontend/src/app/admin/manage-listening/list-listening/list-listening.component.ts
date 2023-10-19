import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Listening} from "../../../core/models/listening";
import {AdminManageListeningService} from "../admin-manage-listening.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {Lesson} from "../../../core/models/lesson";
import {LessonService} from "../../../core/services/lesson.service";
import {data} from "autoprefixer";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-list-listening',
  templateUrl: './list-listening.component.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule],
})
export class ListListeningComponent implements OnInit{
  displayedColumns: string[] = ['id', 'title', 'lesson-name','action'];
  dataSource!: MatTableDataSource<Listening> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  listening :Listening[] = [];
  lesson:Lesson = new Lesson;
  id!: number;

  constructor(
    private manageListeningService:AdminManageListeningService,
    private manageLessonService: LessonService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getListening();
    console.log(this.lesson)
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getListening(){
    this.id = this.route.snapshot.params['id'];
    this.lesson = new Lesson;
    this.manageLessonService.getLessonByID(this.id).subscribe(data => {
      this.lesson = data
      console.log(data)
    });
    this.listening = []
    this.manageListeningService.getListeningByLesson(this.id).subscribe(data => {
      this.listening = data;
      this.dataSource = new MatTableDataSource(this.listening);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.listening)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteListeningDialog(id:number){
    this.dialog.open(ListeningDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getListening());
  }

  openCreateListeningDialog(id:number){
    this.dialog.open(ListeningCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getListening());
  }

  openUpdateListeningDialog(id:number){
    this.dialog.open(ListeningUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getListening());
  }

  getListeningDetail(id:number){
    this.router.navigate(['admin/lesson/listening',id]);
  }
}

@Component({
  selector: 'app-listening-delete-dialog',
  templateUrl: 'listening-delete-dialog.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ListeningDeleteDialog>,
    private manageListeningService:AdminManageListeningService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteListening(id:number){
    this.manageListeningService.deleteListening(id).subscribe(data => {
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
  selector: 'app-listening-create-dialog',
  templateUrl: 'listening-create-dialog.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningCreateDialog {

  listening:Listening =  new Listening;

  constructor(
    public dialogRef: MatDialogRef<ListeningCreateDialog>,
    private manageListeningService:AdminManageListeningService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createListening(){
    console.log(this.listening)
    this.manageListeningService.createListening(this.data,this.listening).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('New listening part added!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-listening-update-dialog',
  templateUrl: 'listening-update-dialog.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningUpdateDialog implements OnInit{

  listening:Listening =  new Listening;

  constructor(
    public dialogRef: MatDialogRef<ListeningUpdateDialog>,
    private manageListeningService:AdminManageListeningService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.listening = new Listening();
    this.manageListeningService.getListeningById(this.data).subscribe(e => {
      this.listening = e
    })
  }

  updateListening(){
    console.log(this.listening)
    this.manageListeningService.updateListening(this.data,this.listening).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('Listening part updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
