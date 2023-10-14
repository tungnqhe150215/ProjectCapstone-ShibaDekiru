import { Component, OnInit, ViewChild} from '@angular/core';
import {MatRippleModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from '../../service/lesson.service';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

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
  selector: 'app-list-lesson',
  templateUrl: './list-lesson.component.html',
  styleUrls: ['./list-lesson.component.css']
})
export class ListLessonComponent implements OnInit{

  displayedColumns: string[] = ['No', 'book', 'name', 'content','created_at','status', 'image'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor (private lessonService: LessonService){

  }
  lesson: Lesson[] =[];
  ngOnInit(): void {
    this.getLesson();
  }

  private getLesson(){
    this.lessonService.getLessonList().subscribe({
      // this.lesson = data;
      next:(res) =>{
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: console.log,
    })
  }
 

  addData() {}
}
