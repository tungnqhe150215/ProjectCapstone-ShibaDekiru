import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/core/models/book';
import { ManageBookService } from '../manage-book.service';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";


@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.css']
})
export class CreateBookComponent implements OnInit{

  book: Book = new Book();
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    private bookService: ManageBookService,
    private nofiService: NotificationService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: number,
    public dialogRef: MatDialogRef<CreateBookComponent>,
  ){}
  ngOnInit(): void {

  }
  // createBook(){
  //   this.bookService.createBook(this.book).subscribe(data =>{
  //     console.log(data);
  //     this.nofiService.openSnackBar('Create Book successful!', 'Cancel');
  //     this.dialogRef.close();
  //   })
  // }

  createBook() {
    if (this.file == null || this.file.size == 0) {
      this.book.image = "";
      this.bookService.createBook(this.book).subscribe({
        next: (data) => {
          console.log(data);
          this.nofiService.openSnackBar('Tạo sách thành công');
          this.dialogRef.close();
        },
        error: (err) => {
          console.error(err);
          this.nofiService.openSnackBar('Tạo sách thất bại vui lòng kiểm tra lại!');
        },
      })
    } else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.book.image = this.drive.fileId

        this.bookService.createBook(this.book).subscribe({
          next: (data) => {
            console.log(data);
            this.nofiService.openSnackBar('Tạo sách thành công');
            this.dialogRef.close();
          },
          error: (err) => {
            console.error(err);
            this.nofiService.openSnackBar('Tạo sách thất bại vui lòng kiểm tra lại!');
          },
        })
      })
    }


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
