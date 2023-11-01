import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { KnowledgeServiceService } from '../knowledge-service.service';

import { MatDialog } from '@angular/material/dialog';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiDetailComponent } from './kanji-detail/kanji-detail.component';


@Component({
  selector: 'app-kanji',
  templateUrl: './kanji.component.html',
  styleUrls: ['./kanji.component.css']
})
export class KanjiComponent implements OnInit{
  public dataSource !: MatTableDataSource<Kanji>;

  displayedColumns: string[] = ['id', 'name','chineseMean', 'lesson' ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private knowledgeService: KnowledgeServiceService,
    private router: Router,
    public dialog: MatDialog,

  ){}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  kanji: Kanji[] = [];

  ngOnInit(): void {
    this.getKanji();
  }
  private getKanji(){
    this.knowledgeService.getAllKanji()
    .subscribe(data =>{
      this.kanji = data;
      this.dataSource = new MatTableDataSource(this.kanji);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data);
    })
  }

  kanjiDetail(id: number){
    this.dialog.open(KanjiDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getKanji());
  }
}
