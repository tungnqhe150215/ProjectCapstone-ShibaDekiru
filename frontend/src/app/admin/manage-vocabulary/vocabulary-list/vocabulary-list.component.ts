import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Lesson } from 'src/app/core/models/lesson';
@Component({
  selector: 'app-vocabulary-list',
  templateUrl: './vocabulary-list.component.html',
  styleUrls: ['./vocabulary-list.component.css'],
})
export class VocabularyListComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'lesson_id',
    'vocabulary_name',
    'hiragana',
    'example',
    'meaning',
  ];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private vocabularyService: VocabularyService) {}
  vocabulary: Vocabulary[] = [];

  status: boolean = true; // Thay đổi giá trị này tùy theo trạng thái
  statusF: boolean = false; // Thay đổi giá trị này tùy theo trạng thái
  ngOnInit(): void {
    this.getVocabulary();
  }

  // Trong component.ts

  //dipsplay Data
  private getVocabulary() {
    this.vocabularyService.getVocabularyList().subscribe({
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
