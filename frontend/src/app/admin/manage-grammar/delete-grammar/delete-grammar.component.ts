import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { GrammarService } from '../grammar.service';

@Component({
  selector: 'app-delete-grammar',
  templateUrl: './delete-grammar.component.html',
  styleUrls: ['./delete-grammar.component.css']
})
export class DeleteGrammarComponent implements OnInit{

  ngOnInit(): void {
    // this.getLesson();
    
  }

  constructor(
    private grammarService: GrammarService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteGrammarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}


  deleteGrammar(id: number) {
    this.grammarService.deleteGrammar(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Đã xóa chữ Hán!');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa chữ Hán!');
      },
    });
  }


}
