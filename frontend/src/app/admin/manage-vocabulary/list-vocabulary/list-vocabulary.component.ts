import { Component, OnInit, ViewChild } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteVocabularyComponent } from '../delete-vocabulary/delete-vocabulary.component';

@Component({
  selector: 'app-list-vocabulary',
  templateUrl: './list-vocabulary.component.html',
  styleUrls: ['./list-vocabulary.component.css'],
})
export class ListVocabularyComponent implements OnInit {
  vocabularies: Vocabulary[]=[];
  displayedColumns: string[] = ['id', 'book', 'name', 'content','created_at','status','action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  filterText: string = '';
  p: number = 1;
  constructor(
    private vocabularyService: VocabularyService,
    private router: Router,
    private nofiService: NotificationService,
    public dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.getVocabularies();
  }
  private getVocabularies() {
    this.vocabularyService.getVocabulariesList().subscribe((data) => {
      this.vocabularies = data;
      this.dataSource = new MatTableDataSource(this.vocabularies);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    });
  }
  vocabularyDetails(id: number) {
    this.router.navigate(['/admin/vocabulary-details', id]);
  }
  updateVocabulary(id: number) {
    this.router.navigate(['/admin/update-vocabulary', id]);
  }
  deleteVocabulary(id: number) {
    this.vocabularyService.deleteVocabulary(id).subscribe((data) => {
      console.log(data);
      this.getVocabularies();
      this.nofiService.openSnackBar('Xóa từ mới thành công');
    });
  }

  openDeleteVocab(id: number){
    this.dialog.open(DeleteVocabularyComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getVocabularies())
  }
  // key: string = 'id';
  // reverse: boolean = false;
  // sort(key: string) {
  //   this.key = key;
  //   this.reverse = !this.reverse;
  // }
}
