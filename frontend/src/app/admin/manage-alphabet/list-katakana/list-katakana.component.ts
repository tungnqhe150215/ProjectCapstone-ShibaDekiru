import { Component, OnInit, ViewChild } from '@angular/core';
import { Katakana } from 'src/app/core/models/katakana';
import { KatakanaService } from '../alphabet-services/katakana.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteKatakanaComponent } from '../delete-katakana/delete-katakana.component';

@Component({
  selector: 'app-list-katakana',
  templateUrl: './list-katakana.component.html',
  styleUrls: ['./list-katakana.component.css']
})
export class ListKatakanaComponent implements OnInit {
  katakanas: Katakana[]=[];
  displayedColumns: string[] = ['id', 'book', 'name','action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  filterText: string = '';
  p: number = 1;
  constructor(
    private katakanaService: KatakanaService,
    private router: Router,
    private nofiService: NotificationService,
    public dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.getKatakana();
  }
  private getKatakana() {
    this.katakanaService.getKatakanaList().subscribe((data) => {
      this.katakanas = data;
      this.dataSource = new MatTableDataSource(this.katakanas);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
  katakanaDetails(id: number) {
    this.router.navigate(['/admin/katakana-details', id]);
  }

  updateKatakana(id: number) {
    this.router.navigate(['/admin/update-katakana', id]);
  }
  deleteKatakana(id: number) {
    this.katakanaService.deleteKatakana(id).subscribe((data) => {
      console.log(data);
      this.getKatakana();
      this.nofiService.openSnackBar('Xóa katakana thành công');
    });
  }

  openDeleteKata(id: number){
    this.dialog.open(DeleteKatakanaComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getKatakana())
  }
  // key: string = 'id';
  // reverse: boolean = false;
  // sort(key: string) {
  //   this.key = key;
  //   this.reverse = !this.reverse;
  // }
}