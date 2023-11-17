import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {Class} from "../../../core/models/class";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {Listening} from "../../../core/models/listening";
import {AdminManageListeningService} from "../../../admin/manage-listening/admin-manage-listening.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {LectureClassService} from "../lecture-class.service";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {Lecture} from "../../../core/models/lecture";
import {SharedModule} from "../../../shared/shared.module";
import { StorageService } from 'src/app/home/auth/user-login/storage.service';

@Component({
  selector: 'app-l-class-list',
  templateUrl: './l-class-list.component.html',
  styleUrls: ['./l-class-list.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatCardModule, SharedModule],
})
export class LClassListComponent {
  displayedColumns: string[] = ['id', 'name', 'status', 'action'];
  dataSource!: MatTableDataSource<Class>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  classes: Class[] = [];
  lecture: Lecture = new Lecture();
  id!: number;

  constructor(
    private manageClassService: LectureClassService,
    private router: Router,
    private storageService: StorageService,
    private route: ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
    this.lecture.lectureId = 1;
  }

  currentUser: any;
  ngOnInit(): void {
    this.getClass();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private getClass() {
    this.currentUser = this.storageService.getUser();
    
    this.classes = []
    this.manageClassService.getClassByLecture(this.currentUser.userAccountId ).subscribe(data => {
      this.classes = data;
      this.dataSource = new MatTableDataSource(this.classes);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openCreateClassDialog(id: number) {
    this.dialog.open(ClassCreateDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getClass());
  }

  openUpdateClassDialog(id: number) {
    this.dialog.open(ClassUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getClass());
  }

  getClassDetail(id: number) {
    this.router.navigate(['lecturer/class/' + id + '/class-work']);
  }
}

//
//
// Here is the code for class-delete component
//
//
//
// Here is the code for class-create component
//
@Component({
  selector: 'app-class-create-dialog',
  templateUrl: 'class-create-dialog.html',
  styleUrls: ['./l-class-list.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class ClassCreateDialog {

  iClass: Class = new Class();

  constructor(
    public dialogRef: MatDialogRef<ClassCreateDialog>,
    private manageClassService: LectureClassService,
    private _snackBar: MatSnackBar,
    private storageService: StorageService,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {
  }
  currentUser: any;
  createClass() {
    
    this.currentUser = this.storageService.getUser();
    console.log(this.iClass)
    this.manageClassService.createClass(this.currentUser.userAccountId , this.iClass).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Class added!!', 'Close', {
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
// Here is the code for class-update component
//
@Component({
  selector: 'app-class-update-dialog',
  templateUrl: 'class-update-dialog.html',
  styleUrls: ['./l-class-list.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class ClassUpdateDialog implements OnInit {

  iClass: Class = new Class();

  constructor(
    public dialogRef: MatDialogRef<ClassUpdateDialog>,
    private manageClassService: LectureClassService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {
  }

  ngOnInit(): void {
    this.iClass = new Class();
    this.manageClassService.getClassById(this.data).subscribe(e => {
      this.iClass = e
    })
  }

  updateClass() {
    console.log(this.iClass)
    console.log(this.data)
    this.manageClassService.updateClass(this.data, this.iClass).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Class updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
