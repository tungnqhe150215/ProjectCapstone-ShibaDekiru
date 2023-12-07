import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { HiraganaService } from '../alphabet-services/hiragana.service';

@Component({
  selector: 'app-delete-hiragana',
  templateUrl: './delete-hiragana.component.html',
  styleUrls: ['./delete-hiragana.component.css']
})
export class DeleteHiraganaComponent implements OnInit{

  ngOnInit(): void {
    
    
  }

  constructor(
    private hiraganaService: HiraganaService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteHiraganaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}


  deleteHira(id: number) {
    this.hiraganaService.deleteHiragana(id).subscribe({
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
