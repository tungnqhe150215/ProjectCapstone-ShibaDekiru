import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/core/models/book';
import { ManageBookService } from '../manage-book.service';


@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.css']
})
export class CreateBookComponent implements OnInit{

  book: Book = new Book();

  constructor(
    private bookService: ManageBookService,
    private nofiService: NotificationService,
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
    });
  }
  

}
