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
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {NgIf} from "@angular/common";
import {FilePreviewService} from "../../../shared/services/file-preview.service";
import { NotificationService } from 'src/app/core/services/notification.service';
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
    private nofiService: NotificationService
  ) {}
  deleteKaiwa(id:number){
    this.manageKaiwaService.deleteKaiwa(id).subscribe(data => {
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Xóa học phần thành công');
    // this._snackBar.open('Xóa học phần thành công', 'Đóng', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
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
    imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, NgIf, SharedModule],
})
export class KaiwaCreateDialog {

  kaiwa:Kaiwa =  new Kaiwa;
  file!: File ;
  drive: Drive = new Drive();
  constructor(
    public dialogRef: MatDialogRef<KaiwaCreateDialog>,
    private manageKaiwaService:AdminManageKaiwaService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  createKaiwa(){

    if (this.file == null || this.file.size == 0) {
      this.kaiwa.link = "";
      this.manageKaiwaService.createKaiwa(this.data,this.kaiwa).subscribe(data => {
        console.log(data)
        this.nofiService.openSnackBar('Đã thêm học phần kaiwa');
        this.dialogRef.close();
      })

    } else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.kaiwa.link = this.drive.fileId

        console.log(this.kaiwa)
        this.manageKaiwaService.createKaiwa(this.data,this.kaiwa).subscribe(data => {
          console.log(data)
          this.dialogRef.close();
        })
        this.nofiService.openSnackBar('Đã thêm học phần kaiwa');
        // this._snackBar.open('Đã thêm học phần kaiwa', 'Đóng', {
        //   horizontalPosition: 'center',
        //   verticalPosition: 'top',
        // });
      })
    }

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}
@Component({
  selector: 'app-kaiwa-update-dialog',
  templateUrl: 'kaiwa-update-dialog.html',
  styleUrls: ['./list-kaiwa.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, SharedModule, NgIf],
})
export class KaiwaUpdateDialog implements OnInit{

  kaiwa:Kaiwa =  new Kaiwa;
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<KaiwaUpdateDialog>,
    private manageKaiwaService:AdminManageKaiwaService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  ngOnInit(): void {
    this.kaiwa = new Kaiwa();
    this.manageKaiwaService.getKaiwaById(this.data).subscribe(e => {
      this.kaiwa = e
    })
  }

  updateKaiwa(){
    if (this.file == null || this.file.size == 0) {
      this.kaiwa.link = "";
      console.log(this.kaiwa)
      this.manageKaiwaService.updateKaiwa(this.data,this.kaiwa).subscribe(data => {
        console.log(data)
        this.dialogRef.close();
      })
      this.nofiService.openSnackBar('Cập nhật học phần thành công');
      // this._snackBar.open('Cập nhật học phần thành công', 'Đóng', {
      //   horizontalPosition: 'center',
      //   verticalPosition: 'top',
      // });
    } else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        this.drive = data as Drive
        this.kaiwa.link = this.drive.fileId
        console.log(this.kaiwa)
        this.manageKaiwaService.updateKaiwa(this.data,this.kaiwa).subscribe(data => {
          console.log(data)
          this.dialogRef.close();
        })
        this.nofiService.openSnackBar('Cập nhật học phần thành công');
        // this._snackBar.open('Cập nhật học phần thành công', 'Đóng', {
        //   horizontalPosition: 'center',
        //   verticalPosition: 'top',
        // });
      })
    }

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}
