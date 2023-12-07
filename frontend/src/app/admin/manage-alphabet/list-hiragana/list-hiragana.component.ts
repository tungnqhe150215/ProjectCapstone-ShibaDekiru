import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteHiraganaComponent } from '../delete-hiragana/delete-hiragana.component';

@Component({
  selector: 'app-list-hiragana',
  templateUrl: './list-hiragana.component.html',
  styleUrls: ['./list-hiragana.component.css'],
})
export class ListHiraganaComponent implements OnInit {
  hiraganas: Hiragana[] = [];
  displayedColumns: string[] = ['id', 'book', 'name','action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  filterText: string = '';
  p: number = 1;
  constructor(
    private hiraganaService: HiraganaService,
    private router: Router,
    private nofiService: NotificationService,
    public dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.getHiragana();
  }
  private getHiragana() {
    this.hiraganaService.getHiraganaList().subscribe((data) => {
      this.hiraganas = data;
      this.dataSource = new MatTableDataSource(this.hiraganas);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    });
  }

  hiraganaDetails(id: number) {
    this.router.navigate(['/admin/hiragana-details', id]);
  }
  updateHiragana(id: number) {
    this.router.navigate(['/admin/update-hiragana', id]);
  }
  deleteHiragana(id: number) {
    this.hiraganaService.deleteHiragana(id).subscribe((data) => {
      console.log(data);
      this.getHiragana();
      this.nofiService.openSnackBar('Xóa hiragana thành công');
    });
  }

  openDeleteHira(id: number){
    this.dialog.open(DeleteHiraganaComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getHiragana())
  }


  // key: string = 'id';
  // reverse: boolean = false;
  // sort(key: string) {
  //   this.key = key;
  //   this.reverse = !this.reverse;
  // }
}
