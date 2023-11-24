import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ManageBookService } from '../manage-book.service';

@Component({
  selector: 'app-delete-book',
  templateUrl: './delete-book.component.html',
  styleUrls: ['./delete-book.component.css']
})
export class DeleteBookComponent  implements OnInit{

  constructor(
    private bookService: ManageBookService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteBookComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ){}
  ngOnInit(): void {
   
  }

  deleteBook(id: number) {
    this.bookService.deleteBook(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Xóa sách thành công');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa sách!');
      },
    });
  }
}
