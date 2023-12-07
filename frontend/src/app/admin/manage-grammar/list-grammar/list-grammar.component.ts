import { Component, OnInit, ViewChild } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { DeleteGrammarComponent } from '../delete-grammar/delete-grammar.component';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-list-grammar',
  templateUrl: './list-grammar.component.html',
  styleUrls: ['./list-grammar.component.css']
})
export class ListGrammarComponent implements OnInit {
  grammars: Grammar[] = [];
  displayedColumns: string[] = ['id', 'book', 'name', 'content','created_at','action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  filterText: string = '';
  p: number = 1;
  constructor(private grammarService: GrammarService, 
    private router: Router,
    private nofiService: NotificationService, 
    public dialog: MatDialog) {}
  ngOnInit(): void {
    this.getGrammars();
  }
  private getGrammars() {
    this.grammarService.getGrammarsList().subscribe((data) => {
      this.grammars = data;
      this.dataSource = new MatTableDataSource(this.grammars);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    });
  }
  grammarDetails(id: number) {
    this.router.navigate(['/admin/grammar-details', id]);
  }
  updateGrammar(id: number) {
    this.router.navigate(['/admin/update-grammar', id]);
  }
  deleteGrammar(id: number) {
    this.grammarService.deleteGrammar(id).subscribe((data) => {
      console.log(data);
      this.getGrammars();
      this.nofiService.openSnackBar('Xóa ngữ pháp thành công');
    });
  }

  openDeleteGrammar(id: number){
    this.dialog.open(DeleteGrammarComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getGrammars())
  }


  // key: string = 'id';
  // reverse: boolean = false;
  // sort(key: string) {
  //   this.key = key;
  //   this.reverse = !this.reverse;
  // }
}
