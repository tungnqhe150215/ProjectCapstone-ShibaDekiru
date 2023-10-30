import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { Katakana } from 'src/app/core/models/katakana';
import { AlphabetService } from '../alphabet.service';
import { KatakanaDetailComponent } from './katakana-detail/katakana-detail.component';

@Component({
  selector: 'app-katakana',
  templateUrl: './katakana.component.html',
  styleUrls: ['./katakana.component.css']
})
export class KatakanaComponent  implements OnInit{
  katakana: Katakana[] = [];

  constructor(
    private alphabetService: AlphabetService,
    private router: Router,
    public dialog: MatDialog,
    private route:ActivatedRoute,

  ){}
  
  ngOnInit(): void {
    this.getAllKatakana();
  }


  private getAllKatakana(){
    this.alphabetService.getAllKatakana()
    .subscribe(data =>{
      this.katakana = data;
      console.log(data);
    })
  }

  chunkedObjects(list: Katakana[], chunkSize: number): Katakana[][] {
    const result: Katakana[][] = [];
    for (let i = 0; i < list.length; i += chunkSize) {
      result.push(list.slice(i, i + chunkSize));
    }
    return result;
  }

  ViewDetail(id:number){
    this.dialog.open(KatakanaDetailComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getAllKatakana())
  }

}
