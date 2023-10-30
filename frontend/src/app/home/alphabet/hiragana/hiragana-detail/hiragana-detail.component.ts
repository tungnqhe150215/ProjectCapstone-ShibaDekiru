import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Hiragana } from 'src/app/core/models/hiragana';
import { AlphabetService } from '../../alphabet.service';

@Component({
  selector: 'app-hiragana-detail',
  templateUrl: './hiragana-detail.component.html',
  styleUrls: ['./hiragana-detail.component.css']
})
export class HiraganaDetailComponent implements OnInit{
  hiragana: Hiragana = new Hiragana;
  // katakana: Katakana[] = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private alphabetService: AlphabetService,
    private router: Router,
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<HiraganaDetailComponent>,

  ){}
  ngOnInit(): void {
    this.hiragana = new Hiragana();
    this.alphabetService.getHiraByID(this.data).subscribe( rs =>{
      this.hiragana = rs;
    })
    
  }

}
