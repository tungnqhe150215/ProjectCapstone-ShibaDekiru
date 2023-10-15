import { Component, OnInit, ViewChild } from '@angular/core';
import { MatRippleModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { Kanji } from 'src/app/core/models/kanji';
import { MatSort, Sort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { KanjiService } from 'src/app/core/services/kanji.service';

// export interface PeriodicElement {
//   name: string;
//   No: number;
//   weight: number;
//   symbol: string;

// }

// const ELEMENT_DATA: PeriodicElement[] = [
//   {No: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
//   {No: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
//   {No: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
//   {No: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
//   {No: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
//   {No: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
//   {No: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
//   {No: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
//   {No: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
//   {No: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
// ];

@Component({
  selector: 'app-list-kanji',
  templateUrl: './list-kanji.component.html',
  styleUrls: ['./list-kanji.component.css'],
})
export class ListKanjiComponent implements OnInit {
  displayedColumns: string[] = [
    'No.',
    'lesson_id',
    'character_kanji',
    'chinese_mean',
    'example',
    'kun_reading',
    'on_reading',
  ];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private kanjiService: KanjiService) {}
  kanji: Kanji[] = [];

  status: boolean = true; // Thay đổi giá trị này tùy theo trạng thái
  statusF: boolean = false; // Thay đổi giá trị này tùy theo trạng thái
  ngOnInit(): void {
    this.getKanji();
  }

  // Trong component.ts

  //dipsplay Data
  private getKanji() {
    this.kanjiService.getKanjiList().subscribe({
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
