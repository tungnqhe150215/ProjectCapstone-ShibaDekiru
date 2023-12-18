import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Reading} from "../../../core/models/reading";
import {AdminManageReadingService} from "../admin-manage-reading.service";
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
  selector: 'app-list-reading',
  templateUrl: './list-reading.component.html',
  styleUrls: ['./list-reading.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule,SharedModule],
})
export class ListReadingComponent implements OnInit{
  displayedColumns: string[] = ['id', 'title', 'lesson-name','action'];
  dataSource!: MatTableDataSource<Reading> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  reading :Reading[] = [];
  lesson:Lesson = new Lesson;
  id!: number;

  constructor(
    private manageReadingService:AdminManageReadingService,
    private manageLessonService: LessonService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getReading();
    console.log(this.lesson)
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getReading(){
    this.id = this.route.snapshot.params['id'];
    this.lesson = new Lesson;
    this.manageLessonService.getLessonByID(this.id).subscribe(data => {
      this.lesson = data
      console.log(data)
    });
    this.reading = []
    this.manageReadingService.getReadingByLesson(this.id).subscribe(data => {
      this.reading = data;
      this.dataSource = new MatTableDataSource(this.reading);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.reading)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteReadingDialog(id:number){
    this.dialog.open(ReadingDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getReading());
  }

  openCreateReadingDialog(id:number){
    this.dialog.open(ReadingCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getReading());
  }

  openUpdateReadingDialog(id:number){
    this.dialog.open(ReadingUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getReading());
  }

  getReadingDetail(id:number){
    this.router.navigate(['admin/lesson/reading',id]);
  }
}

@Component({
  selector: 'app-reading-delete-dialog',
  templateUrl: 'reading-delete-dialog.html',
  styleUrls: ['./list-reading.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ReadingDeleteDialog>,
    private manageReadingService:AdminManageReadingService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}
  deleteReading(id:number){
    this.manageReadingService.deleteReading(id).subscribe(data => {
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Học phần đọc đã xóa');
    // this._snackBar.open('Deleted!!', 'Close', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-reading-create-dialog',
  templateUrl: 'reading-create-dialog.html',
  styleUrls: ['./list-reading.component.css'],
  standalone: true,
    imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, NgIf, SharedModule],
})
export class ReadingCreateDialog {

  reading:Reading =  new Reading;
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ReadingCreateDialog>,
    private manageReadingService:AdminManageReadingService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  createReading(){

    this.fileService.uploadFile(this.file).subscribe(data => {
      console.log(data)
      this.drive = data as Drive
      this.reading.image =  this.drive.fileId
      console.log(this.reading)
      this.manageReadingService.createReading(this.data,this.reading).subscribe(data => {
        console.log(data)
        this.dialogRef.close();
      })
      this.nofiService.openSnackBar('Học phần đọc đã được thêm');
      // this._snackBar.open('Học phần đọc đã thêm', 'Close', {
      //   horizontalPosition: 'center',
      //   verticalPosition: 'top',
      // });
    })

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
  selector: 'app-reading-update-dialog',
  templateUrl: 'reading-update-dialog.html',
  styleUrls: ['./list-reading.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, NgIf, SharedModule],
})
export class ReadingUpdateDialog implements OnInit{

  reading:Reading =  new Reading;
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ReadingUpdateDialog>,
    private manageReadingService:AdminManageReadingService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  ngOnInit(): void {
    this.reading = new Reading();
    this.manageReadingService.getReadingById(this.data).subscribe(e => {
      this.reading = e
    })
  }

  updateReading(){
    if (this.file == null || this.file.size == 0) {
      this.reading.image = "";
      console.log(this.reading)
      this.manageReadingService.updateReading(this.data,this.reading).subscribe(data => {
        console.log(data)
        this.dialogRef.close();
      })
      this.nofiService.openSnackBar('Học phần đọc đã được cập nhật');
      // this._snackBar.open('Reading part updated!!', 'Close', {
      //   horizontalPosition: 'center',
      //   verticalPosition: 'top',
      // });

    } else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        this.drive = data as Drive
        this.reading.image = this.drive.fileId
        console.log(this.reading)
        this.manageReadingService.updateReading(this.data,this.reading).subscribe(data => {
          console.log(data)
          this.dialogRef.close();
        })
        this.nofiService.openSnackBar('Học phần đọc đã được cập nhật');
        // this._snackBar.open('Reading part updated!!', 'Close', {
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
