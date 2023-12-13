import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatDialog } from '@angular/material/dialog';
import { Listening } from 'src/app/core/models/listening';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-list-listening',
  templateUrl: './list-listening.component.html',
  styleUrls: ['./list-listening.component.css']
})
export class ListListeningComponent implements OnInit {

  public dataSource !: MatTableDataSource<Listening>;

  displayedColumns: string[] = ['id', 'name', 'lesson','script'];
  slicedItems: string[][] = []; // Khai báo ở đây
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  listening: Listening[] = [];
  lesson: Lesson = new Lesson;
  
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getListenByLessonID();
  }

  id!:number
  getListenByLessonID() {
    // const idLesson = this.studentLessonService.getLessonID();
    this.id = this.route.snapshot.params['idL'];
    this.listening = [];
    this.studentLessonService.getListeningByLesson(this.id)
    .subscribe({
      next:data =>{
        this.listening = data;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.slicedItems = this.getSlicedItems(this.listening, 'script');
        console.log(data)
      }
    })

  }

  getSlicedItems(data: any[], propertyName: string): string[][] {
    return data.map(item => item[propertyName]
      .split('。')
      .filter((subItem: string) => subItem.trim() !== '')
    );
  }

  getLessonById() {
    this.lesson = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(data => {
      this.lesson = data
    })
  }
}
