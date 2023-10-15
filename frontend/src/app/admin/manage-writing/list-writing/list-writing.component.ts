import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Writing} from "../../../core/models/writing";
import {AdminManageWritingService} from "../admin-manage-writing.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {Lesson} from "../../../core/models/lesson";
import {LessonService} from "../../../core/services/lesson.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-list-writing',
  templateUrl: './list-writing.component.html',
  styleUrls: ['./list-writing.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule],
})
export class ListWritingComponent implements OnInit{
  displayedColumns: string[] = ['id', 'topic', 'lesson-name','action'];
  dataSource!: MatTableDataSource<Writing> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  writing :Writing[] = [];
  lesson:Lesson = new Lesson;
  id!: number;

  constructor(
    private manageWritingService:AdminManageWritingService,
    private manageLessonService: LessonService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getWriting();
    console.log(this.lesson)
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getWriting(){
    this.id = this.route.snapshot.params['id'];
    this.lesson = new Lesson;
    this.manageLessonService.getLessonByID(this.id).subscribe(data => {
      this.lesson = data
      console.log(data)
    });
    this.writing = []
    this.manageWritingService.getWritingByLesson(this.id).subscribe(data => {
      this.writing = data;
      this.dataSource = new MatTableDataSource(this.writing);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.writing)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteWritingDialog(id:number){
      this.dialog.open(WritingDeleteDialog, {
        data: id
      }).afterClosed().subscribe(() => this.getWriting());
  }

  openCreateWritingDialog(id:number){
    this.dialog.open(WritingCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getWriting());
  }

  openUpdateWritingDialog(id:number){
    this.dialog.open(WritingUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getWriting());
  }

  getWritingDetail(id:number){
    this.router.navigate(['writing',id]);
  }
}

@Component({
  selector: 'app-writing-delete-dialog',
  templateUrl: 'writing-delete-dialog.html',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingDeleteDialog {
  constructor(
      public dialogRef: MatDialogRef<WritingDeleteDialog>,
      private manageWritingService:AdminManageWritingService,
      @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteWriting(id:number){
    this.manageWritingService.deleteWriting(id).subscribe(data => {
      this.dialogRef.close();
    })
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-writing-create-dialog',
  templateUrl: 'writing-create-dialog.html',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingCreateDialog {

  writing:Writing =  new Writing;

  constructor(
      public dialogRef: MatDialogRef<WritingDeleteDialog>,
      private manageWritingService:AdminManageWritingService,
      @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createWriting(){
    console.log(this.writing)
    this.manageWritingService.createWriting(this.data,this.writing).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-writing-update-dialog',
  templateUrl: 'writing-update-dialog.html',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class WritingUpdateDialog implements OnInit{

  writing:Writing =  new Writing;

  constructor(
    public dialogRef: MatDialogRef<WritingDeleteDialog>,
    private manageWritingService:AdminManageWritingService,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
      this.writing = new Writing();
      this.manageWritingService.getWritingById(this.data).subscribe(e => {
        this.writing = e
      })
  }

  updateWriting(){
    console.log(this.writing)
    this.manageWritingService.updateWriting(this.data,this.writing).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
