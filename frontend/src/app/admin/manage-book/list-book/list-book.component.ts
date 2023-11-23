import { Component, OnInit, ViewChild } from '@angular/core';
import {MatRippleModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {ActivatedRoute, Route} from "@angular/router";
import { ManageBookService } from '../manage-book.service';
import { Book } from 'src/app/core/models/book';
import { CreateBookComponent } from '../create-book/create-book.component';
import { UpdateBookComponent } from '../update-book/update-book.component';
import { BookDetailComponent } from '../book-detail/book-detail.component';
import { DeleteBookComponent } from '../delete-book/delete-book.component';


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
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.css']
})
export class ListBookComponent implements OnInit{

  displayedColumns: string[] = ['No', 'name', 'description', 'image', 'action'];
  dataSource !: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  
  constructor(
    private bookService: ManageBookService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialog: MatDialog
  ){}

  book: Book[] = [];
  ngOnInit(): void {
    this.ListBook();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private ListBook(){
    this.bookService.getAllBook().subscribe({
      next:(value) =>{
          this.dataSource = new MatTableDataSource(value);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
      },
      error: console.log,
    })
  }

  addData() {
    this.dialog.open(CreateBookComponent, {

    }).afterClosed().subscribe(() => this.ListBook())
  }


  updateUpdateBook(id:number){
    this.dialog.open(UpdateBookComponent, {
      data:id
    }).afterClosed().subscribe(() => this.ListBook())
  }

  bookDetail(id: number){
    this.dialog.open(BookDetailComponent,{
      data:id
    })
  }

  openDeleteBook(id:number){
    this.dialog.open(DeleteBookComponent, {
      data:id
    }).afterClosed().subscribe( () => this.ListBook())
  }
  
  deleteBook(id:number){
    this.bookService.deleteBook(id).subscribe({
      next:(res) =>{
        this.nofiService.openSnackBar('Book deleted!', 'Cancel');
        this.ListBook();
      }
    })
  }
  GotoBookLesson(id:number){
    this.router.navigate(['admin/book/'+id+'/lesson']);
  }
}
