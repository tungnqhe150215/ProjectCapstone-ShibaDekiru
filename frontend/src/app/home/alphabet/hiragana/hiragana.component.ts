import { Component, OnInit } from '@angular/core';
import { Hiragana } from 'src/app/core/models/hiragana';
import { Katakana } from 'src/app/core/models/katakana';
import { AlphabetService } from '../alphabet.service';
import { ActivatedRoute, Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { HiraganaDetailComponent } from './hiragana-detail/hiragana-detail.component';

@Component({
  selector: 'app-hiragana',
  templateUrl: './hiragana.component.html',
  styleUrls: ['./hiragana.component.css']
})
export class HiraganaComponent implements OnInit{

  hiragana: Hiragana[] = [];
  // katakana: Katakana[] = [];
  constructor(
    private alphabetService: AlphabetService,
    private router: Router,
    public dialog: MatDialog,
    private route:ActivatedRoute,

  ){}

  ngOnInit(): void {
    this.getAllHiragana();
  }

  private getAllHiragana(){
    this.alphabetService.getAllHiragana()
    .subscribe(data =>{
      this.hiragana = data;
      console.log(data);
    })
  }

  chunkedObjects(list: Hiragana[], chunkSize: number): Hiragana[][] {
    const result: Hiragana[][] = [];
    for (let i = 0; i < list.length; i += chunkSize) {
      result.push(list.slice(i, i + chunkSize));
    }
    return result;
  }

  ViewDetail(id:number){
    this.dialog.open(HiraganaDetailComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getAllHiragana())
  }
}


