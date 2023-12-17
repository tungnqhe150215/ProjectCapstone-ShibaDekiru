import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ManageBookService } from '../manage-book.service';
import { Book } from 'src/app/core/models/book';
import {FileService} from "../../../shared/services/file.service";
import {data} from "autoprefixer";
import {Drive} from "../../../core/models/drive";
import {FilePreviewService} from "../../../shared/services/file-preview.service";

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit{

  book: Book = new Book();
  file!: File ;
  drive: Drive = new Drive();
  constructor(
    private bookService: ManageBookService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
    private nofiService: NotificationService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: number,
    public dialogRef: MatDialogRef<UpdateBookComponent>,
  ){}
  ngOnInit(): void {
    this.book = new Book();
    this.bookService.getBookByID(this.data).subscribe(res =>{
      this.book = res
    })
  }
  // updateBook(){
  //   this.bookService.updateBook(this.data, this.book).subscribe(data =>{
  //     console.log(data)
  //     this.dialogRef.close();
  //     this.nofiService.openSnackBar('Update successful!', 'Cancel');
  //   })
  // }
  updateBook() {
    if (this.file == null || this.file.size == 0)  {

      this.book.image = "";
      this.bookService.updateBook(this.data, this.book).subscribe({
        next: (data) => {
          console.log(data);
          this.dialogRef.close();
          this.nofiService.openSnackBar('Cập nhật sách thành công');
        },
        error: (err) => {
          console.error(err);
          this.nofiService.openSnackBar('Đã xảy ra lỗi khi cập nhật sách');
        },
      });

    } else {

      this.fileService.uploadFile(this.file).subscribe(data=> {
        console.log(data)
        this.drive = data as Drive
        this.book.image = this.drive.fileId

        this.bookService.updateBook(this.data, this.book).subscribe({
          next: (data) => {
            console.log(data);
            this.dialogRef.close();
            this.nofiService.openSnackBar('Cập nhật sách thành công');
          },
          error: (err) => {
            console.error(err);
            this.nofiService.openSnackBar('Đã xảy ra lỗi khi cập nhật sách');
          },
        });
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
