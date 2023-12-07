import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { DeleteKanjiComponent } from '../delete-kanji/delete-kanji.component';


@Component({
  selector: 'app-list-kanji',
  templateUrl: './list-kanji.component.html',
  styleUrls: ['./list-kanji.component.css'],
})
export class ListKanjiComponent implements OnInit {
  kanjis: Kanji[] = [];

  displayedColumns: string[] = ['id', 'book', 'name', 'content','created_at','status', 'image','action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  filterText: string = '';
  p: number = 1;
  constructor(private kanjiService: KanjiService, private router: Router, private nofiService: NotificationService, public dialog: MatDialog) {}
  ngOnInit(): void {
    this.getKanjis();
  }
  private getKanjis() {
    this.kanjiService.getKanjisList().subscribe((data) => {
      this.kanjis = data;
      this.dataSource = new MatTableDataSource(this.kanjis);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    });
  }
  kanjiDetails(id: number) {
    this.router.navigate(['/admin/kanji-details', id]);
  }
  updateKanji(kanjiId: number) {
    this.router.navigate(['/admin/update-kanji', kanjiId]);
  }
  deleteKanji(id: number) {
    this.kanjiService.deleteKanji(id).subscribe((data) => {
      console.log(data);
      this.getKanjis();
      this.nofiService.openSnackBar('Xóa kanji thành công');
    });
  }

  openDeleteKanji(id: number){
    this.dialog.open(DeleteKanjiComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getKanjis())
  }

  
  // key: string = 'id';
  // reverse: boolean = false;
  // sort1(key: string) {
  //   this.key = key;
  //   this.reverse = !this.reverse;
  // }
}
