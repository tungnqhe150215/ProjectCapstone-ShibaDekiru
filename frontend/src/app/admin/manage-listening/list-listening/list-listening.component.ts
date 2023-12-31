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
import {SharedModule} from "../../../shared/shared.module";
import {NgIf} from "@angular/common";
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";
import { NotificationService } from 'src/app/core/services/notification.service';
import {MatCardModule} from "@angular/material/card";

@Component({
  selector: 'app-list-listening',
  templateUrl: './list-listening.component.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule,SharedModule],
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
    private nofiService: NotificationService
  ) {}
  deleteListening(id:number){
    this.manageListeningService.deleteListening(id).subscribe(data => {
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Xóa bài nghe thành công');
    // this._snackBar.open('Xóa bài nghe thành công', 'Đóng', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
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
    imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, NgIf, SharedModule],
})
export class ListeningCreateDialog {

  listening:Listening =  new Listening;
  file!: File;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ListeningCreateDialog>,
    private manageListeningService:AdminManageListeningService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  createListening(){
    if (this.file) {
      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(this.listening)
        this.drive = data as Drive
        this.listening.link = this.drive.fileId;
        this.manageListeningService.createListening(this.data,this.listening).subscribe(data => {
          console.log(data)
          this.dialogRef.close();
        })
        this.nofiService.openSnackBar('Tạo bài nghe thành công');
        // this._snackBar.open('Tạo bài nghe thành công', 'Đóng', {
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
  selector: 'app-listening-update-dialog',
  templateUrl: 'listening-update-dialog.html',
  styleUrls: ['./list-listening.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, SharedModule, NgIf, MatCardModule],
})
export class ListeningUpdateDialog implements OnInit{

  listening:Listening =  new Listening;
  file!: File;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ListeningUpdateDialog>,
    private manageListeningService:AdminManageListeningService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {}

  ngOnInit(): void {
    this.listening = new Listening();
    this.manageListeningService.getListeningById(this.data).subscribe(e => {
      this.listening = e
    })
  }

  updateListening(){
    if (this.file == null || this.file.size == 0) {
      this.listening.link = "";
      console.log(this.listening)
      this.manageListeningService.updateListening(this.data,this.listening).subscribe(data => {
        console.log(data)
        this.dialogRef.close();
      })
      this.nofiService.openSnackBar('Cập nhật bài nghe thành công');
      // this._snackBar.open('Cập nhật bài nghe thành công', 'Đóng', {
      //   horizontalPosition: 'center',
      //   verticalPosition: 'top',
      // });
    } else {

      this.fileService.uploadFile(this.file).subscribe(data => {
        this.drive = data as Drive
        this.listening.link = this.drive.fileId
        this.manageListeningService.updateListening(this.data,this.listening).subscribe(data => {
          console.log(data)
          this.dialogRef.close();
        })
        this.nofiService.openSnackBar('Cập nhật bài nghe thành công');
        // this._snackBar.open('Cập nhật bài nghe thành công', 'Đóng', {
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
