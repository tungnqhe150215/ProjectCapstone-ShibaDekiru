import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { KnowledgeServiceService } from '../knowledge-service.service';

import { MatDialog } from '@angular/material/dialog';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabDetailComponent } from './vocab-detail/vocab-detail.component';


@Component({
  selector: 'app-vocab',
  templateUrl: './vocab.component.html',
  styleUrls: ['./vocab.component.css']
})
export class VocabComponent implements OnInit{
  public dataSource !: MatTableDataSource<Vocabulary>;

  displayedColumns: string[] = ['id', 'name','meaning', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  vocab: Vocabulary[] =[];
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

  ngOnInit(): void {
   this.getVocab();
  }
  private getVocab(){
    this.knowledgeService.getAllVocab()
    .subscribe(data =>{
      this.vocab = data;
      this.dataSource = new MatTableDataSource(this.vocab);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data);
    })
  }

  vocabDetail(id: number){
    this.dialog.open(VocabDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getVocab());
  }
}
