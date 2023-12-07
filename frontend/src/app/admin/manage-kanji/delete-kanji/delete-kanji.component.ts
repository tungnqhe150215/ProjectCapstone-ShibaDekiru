import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { KanjiService } from '../kanji.service';

@Component({
  selector: 'app-delete-kanji',
  templateUrl: './delete-kanji.component.html',
  styleUrls: ['./delete-kanji.component.css']
})
export class DeleteKanjiComponent implements OnInit{

  ngOnInit(): void {
    // this.getLesson();
    
  }
  constructor(
    private kanjiService: KanjiService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteKanjiComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}


  // deleteKanji1(id: number) {
  //   this.kanjiService.deleteKanji(id).subscribe((data) => {
  //     console.log(data);
  //     this.getKanjis();
  //     this.nofiService.openSnackBar('Xóa kanji thành công');
  //   });
  // }

  deleteKanji(id: number) {
    this.kanjiService.deleteKanji(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Đã xóa chữ Hán!');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa bài học!');
      },
    });
  }
}
