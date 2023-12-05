import { Component, OnInit, ViewChild } from '@angular/core';
import { CreateLessonComponent } from '../create-lesson/create-lesson.component';
import { LessonDetailComponent } from '../lesson-detail/lesson-detail.component';
import { UpdateLessonComponent } from '../update-lesson/update-lesson.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { LessonService } from 'src/app/core/services/lesson.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ManageBookService } from '../../manage-book/manage-book.service';
import { Book } from 'src/app/core/models/book';
import { Lesson } from 'src/app/core/models/lesson';
import { DeleteLessonComponent } from '../delete-lesson/delete-lesson.component';

@Component({
  selector: 'app-list-all-lesson',
  templateUrl: './list-all-lesson.component.html',
  styleUrls: ['./list-all-lesson.component.css']
})
export class ListAllLessonComponent implements OnInit{

  displayedColumns: string[] = ['id', 'book', 'name', 'content','created_at','status', 'image','action'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(
    private bookService: ManageBookService,
    private lessonService: LessonService, 
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialog: MatDialog
  ){}

  lesson: Lesson[] =[];
  book: Book= new Book;
  id!: number;
  
  ngOnInit(): void {
    this.getLesson();

    
  }
  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      this.dataSource = new MatTableDataSource(this.lesson);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
  
  }



  //test paging
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
 
  openCreateLessonialog(id:number){
    this.dialog.open(CreateLessonComponent, {
      data:id
    }).afterClosed().subscribe(() => this.getLesson())
  }
  

  LessonDetail(id:number){
    this.dialog.open(LessonDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getLesson())
  }

  

  lessonDetail(id:number){
    this.router.navigate(['admin/book/lesson/lesson-detail',id]);
  }
  
  // openUpdateLessonialog(id:number){
  //   this.dialog.open(UpdateLessonComponent,
  //   data:id  )
  // }
  UpdateLesson(id: number){
    this.dialog.open(UpdateLessonComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getLesson())
  }
  updateLesson(id:number){
    this.router.navigate(['admin/lesson/update-lesson',id]);
  }

  //delete form 
  openDeleteLesson(id: number){
    this.dialog.open(DeleteLessonComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getLesson())
  }

  deleteLesson(id: number) {
    this.lessonService.deleteLesson(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Đã xóa bài học!');
        this.getLesson();
      },
      error: console.log,
    });
  }
}
