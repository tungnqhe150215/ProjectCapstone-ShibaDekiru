import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { CreateBookComponent } from '../create-book/create-book.component';
import { ManageBookService } from '../manage-book.service';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit{

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
   this.book = new Book();
   this.bookService.getBookByID(this.data).subscribe(res =>{
    this.book = res
   })
  }

}
