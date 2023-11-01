import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { KnowledgeServiceService } from '../../knowledge-service.service';
import { Grammar } from 'src/app/core/models/grammar';

@Component({
  selector: 'app-grammar-detail',
  templateUrl: './grammar-detail.component.html',
  styleUrls: ['./grammar-detail.component.css']
})
export class GrammarDetailComponent implements OnInit{

  grammar: Grammar = new Grammar;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private router: Router,
    private route:ActivatedRoute,
    private knowledgeService: KnowledgeServiceService,
    public dialogRef: MatDialogRef<GrammarDetailComponent>,
  ){}
  ngOnInit(): void {
   this.grammar = new Grammar();
   this.knowledgeService.getGrammarById(this.data).subscribe(
    rs =>{ 
      this.grammar = rs;
    }
   ) 
  }

}
