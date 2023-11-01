import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { KnowledgeServiceService } from '../knowledge-service.service';
import { Grammar } from 'src/app/core/models/grammar';
import { MatDialog } from '@angular/material/dialog';
import { GrammarDetailComponent } from './grammar-detail/grammar-detail.component';

@Component({
  selector: 'app-grammar',
  templateUrl: './grammar.component.html',
  styleUrls: ['./grammar.component.css']
})
export class GrammarComponent implements OnInit{
  public dataSource !: MatTableDataSource<Grammar>;

  displayedColumns: string[] = ['id', 'name', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  grammar: Grammar[] = [];

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
    this.GetGrammar();
  }

  private GetGrammar(){
    this.knowledgeService.getAllGrammar()
    .subscribe(data =>{
      this.grammar = data;
      this.dataSource = new MatTableDataSource(this.grammar);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data);
    })
  }

  grammarDetail(id: number){
    this.dialog.open(GrammarDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.GetGrammar());
  }
}
