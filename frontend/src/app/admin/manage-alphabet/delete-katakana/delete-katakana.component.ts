import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { KatakanaService } from '../alphabet-services/katakana.service';

@Component({
  selector: 'app-delete-katakana',
  templateUrl: './delete-katakana.component.html',
  styleUrls: ['./delete-katakana.component.css']
})
export class DeleteKatakanaComponent implements OnInit{
  ngOnInit(): void {
    
  }

  constructor(
    private katakanaService: KatakanaService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteKatakanaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}

  deleteKata(id: number) {
    this.katakanaService.deleteKatakana(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Xóa chữ cái thành công!');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa chữ cái!');
      },
    });
  }
}
