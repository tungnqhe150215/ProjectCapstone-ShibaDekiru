import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { KnowledgeServiceService } from '../../knowledge-service.service';
import { Vocabulary } from 'src/app/core/models/vocabulary';

@Component({
  selector: 'app-vocab-detail',
  templateUrl: './vocab-detail.component.html',
  styleUrls: ['./vocab-detail.component.css']
})
export class VocabDetailComponent implements OnInit{

  vocab: Vocabulary = new Vocabulary;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private router: Router,
    private route:ActivatedRoute,
    private knowledgeService: KnowledgeServiceService,
    public dialogRef: MatDialogRef<VocabDetailComponent>,
  ){}

  ngOnInit(): void {
    this.vocab = new Vocabulary();
    this.knowledgeService.getVocabById(this.data).subscribe(
     rs =>{ 
       this.vocab = rs;
     }
    ) 
   }
}
