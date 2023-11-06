import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import { Grammar } from 'src/app/core/models/grammar';
import { MatDialog } from '@angular/material/dialog';
import { GrammarDetailComponent } from '../../list-knowledge/grammar/grammar-detail/grammar-detail.component';

@Component({
  selector: 'app-list-grammarless',
  templateUrl: './list-grammarless.component.html',
  styleUrls: ['./list-grammarless.component.css']
})
export class ListGrammarlessComponent implements OnInit {

  public dataSource !: MatTableDataSource<Grammar>;

  displayedColumns: string[] = ['id', 'name','structure','explain','Example', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  grammar: Grammar[]=[];
  id !: number;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
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
    this.getGrammarByLesson();
  }

  getGrammarByLesson(){
    const idLesson = this.studentLessonService.getLessonID();
    this.grammar = [];
    this.studentLessonService.getGrammarByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.grammar = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }
  grammarDetail(id: number){
    this.dialog.open(GrammarDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getGrammarByLesson());
  }
  
}
