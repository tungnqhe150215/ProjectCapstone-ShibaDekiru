import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { DeleteGrammarComponent } from '../../manage-grammar/delete-grammar/delete-grammar.component';
import { VocabularyService } from '../vocabulary.service';

@Component({
  selector: 'app-delete-vocabulary',
  templateUrl: './delete-vocabulary.component.html',
  styleUrls: ['./delete-vocabulary.component.css']
})
export class DeleteVocabularyComponent implements OnInit{

  ngOnInit(): void {
    
    
  }

  constructor(
    private vocabularyService: VocabularyService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteGrammarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}

  

  deleteVocabulary(id: number) {
    this.vocabularyService.deleteVocabulary(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Xóa từ mới thành công!');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa từ mới!');
      },
    });
  }
}
