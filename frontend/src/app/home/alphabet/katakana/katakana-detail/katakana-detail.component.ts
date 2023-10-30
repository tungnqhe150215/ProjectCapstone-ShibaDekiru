import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Katakana } from 'src/app/core/models/katakana';
import { AlphabetService } from '../../alphabet.service';

@Component({
  selector: 'app-katakana-detail',
  templateUrl: './katakana-detail.component.html',
  styleUrls: ['./katakana-detail.component.css']
})
export class KatakanaDetailComponent implements OnInit{

  katakana: Katakana = new Katakana;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private alphabetService: AlphabetService,
    private router: Router,
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<KatakanaDetailComponent>,

  ){}
  ngOnInit(): void {
    this.katakana = new Katakana();
    this.alphabetService.getKataByID(this.data).subscribe( rs =>{
      this.katakana = rs;
    })
  }

  
}
