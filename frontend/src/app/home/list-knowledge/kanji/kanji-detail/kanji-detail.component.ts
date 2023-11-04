import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { KnowledgeServiceService } from '../../knowledge-service.service';
import { Kanji } from 'src/app/core/models/kanji';

@Component({
  selector: 'app-kanji-detail',
  templateUrl: './kanji-detail.component.html',
  styleUrls: ['./kanji-detail.component.css']
})
export class KanjiDetailComponent implements OnInit{

  kanji: Kanji = new Kanji();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private router: Router,
    private route:ActivatedRoute,
    private knowledgeService: KnowledgeServiceService,
    public dialogRef: MatDialogRef<KanjiDetailComponent>,
  ){}

  ngOnInit(): void {
    this.kanji = new Kanji();
    this.knowledgeService.getKanjiById(this.data).subscribe(
      rs =>{
        this.kanji = rs;
      }
    )
  }
    
}
