import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { GrammarService } from '../grammar.service';
import { Grammar } from 'src/app/core/models/grammar';

@Component({
  selector: 'app-list-grammar',
  templateUrl: './list-grammar.component.html',
  styleUrls: ['./list-grammar.component.css']
})
export class ListGrammarComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'lesson_id',
    'name',
    'structure',
    'description',
    'example',
  ];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private grammarService: GrammarService) {}
  grammar: Grammar[] = [];

  status: boolean = true; // Thay đổi giá trị này tùy theo trạng thái
  statusF: boolean = false; // Thay đổi giá trị này tùy theo trạng thái
  ngOnInit(): void {
    this.getGrammar();
  }

  // Trong component.ts

  //dipsplay Data
  private getGrammar() {
    this.grammarService.getGrammarList().subscribe({
      // this.lesson = data;
      next: (res) => {
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: console.log,
    });
  }

  //test paging
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //edit Form

  //insert form
  addData() {}

  //delete form
}

