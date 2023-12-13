import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Kaiwa} from "../../../core/models/kaiwa";
import {AdminManageKaiwaService} from "../admin-manage-kaiwa.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {Lesson} from "../../../core/models/lesson";
import {LessonService} from "../../../core/services/lesson.service";
import {data} from "autoprefixer";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SharedModule} from "../../../shared/shared.module";
@Component({
  selector: 'app-list-kaiwa',
  templateUrl: './list-kaiwa.component.html',
  styleUrls: ['./list-kaiwa.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule,SharedModule],
})
export class ListKaiwaComponent implements OnInit{
  displayedColumns: string[] = ['id', 'title', 'lesson-name','script','action'];
  dataSource!: MatTableDataSource<Kaiwa> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  kaiwa :Kaiwa[] = [];
  lesson:Lesson = new Lesson;
  id!: number;

  constructor(
    private manageKaiwaService:AdminManageKaiwaService,
    private manageLessonService: LessonService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getKaiwa();
    console.log(this.lesson)
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getKaiwa(){
    this.id = this.route.snapshot.params['id'];
    this.lesson = new Lesson;
    this.manageLessonService.getLessonByID(this.id).subscribe(data => {
      this.lesson = data
      console.log(data)
    });
    this.kaiwa = []
    this.manageKaiwaService.getKaiwaByLesson(this.id).subscribe(data => {
      this.kaiwa = data;
      this.dataSource = new MatTableDataSource(this.kaiwa);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.kaiwa)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteKaiwaDialog(id:number){
    this.dialog.open(KaiwaDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getKaiwa());
  }

  openCreateKaiwaDialog(id:number){
    this.dialog.open(KaiwaCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getKaiwa());
  }

  openUpdateKaiwaDialog(id:number){
    this.dialog.open(KaiwaUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getKaiwa());
  }
}

@Component({
  selector: 'app-kaiwa-delete-dialog',
  templateUrl: 'kaiwa-delete-dialog.html',
  styleUrls: ['./list-kaiwa.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class KaiwaDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<KaiwaDeleteDialog>,
    private manageKaiwaService:AdminManageKaiwaService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteKaiwa(id:number){
    this.manageKaiwaService.deleteKaiwa(id).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Xóa học phần thành công', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-kaiwa-create-dialog',
  templateUrl: 'kaiwa-create-dialog.html',
  styleUrls: ['./list-kaiwa.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class KaiwaCreateDialog {

  kaiwa:Kaiwa =  new Kaiwa;

  constructor(
    public dialogRef: MatDialogRef<KaiwaCreateDialog>,
    private manageKaiwaService:AdminManageKaiwaService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createKaiwa(){
    console.log(this.kaiwa)
    this.manageKaiwaService.createKaiwa(this.data,this.kaiwa).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('Đã thêm học phần kaiwa', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-kaiwa-update-dialog',
  templateUrl: 'kaiwa-update-dialog.html',
  styleUrls: ['./list-kaiwa.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class KaiwaUpdateDialog implements OnInit{

  kaiwa:Kaiwa =  new Kaiwa;

  constructor(
    public dialogRef: MatDialogRef<KaiwaUpdateDialog>,
    private manageKaiwaService:AdminManageKaiwaService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.kaiwa = new Kaiwa();
    this.manageKaiwaService.getKaiwaById(this.data).subscribe(e => {
      this.kaiwa = e
    })
  }

  updateKaiwa(){
    console.log(this.kaiwa)
    this.manageKaiwaService.updateKaiwa(this.data,this.kaiwa).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('Cập nhật học phần thành công', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
