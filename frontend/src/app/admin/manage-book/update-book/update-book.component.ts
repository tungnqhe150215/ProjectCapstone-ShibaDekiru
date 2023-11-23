import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ManageBookService } from '../manage-book.service';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit{

  book: Book = new Book();

  constructor(
    private bookService: ManageBookService,
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
  }
  


}
