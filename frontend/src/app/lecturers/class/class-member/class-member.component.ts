import {Component, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Class} from "../../../core/models/class";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Lecture} from "../../../core/models/lecture";
import {LectureClassService} from "../lecture-class.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ClassCreateDialog, ClassUpdateDialog} from "../l-class-list/l-class-list.component";
import {ClassStudent} from "../../../core/models/class-student";
import {data} from "autoprefixer";

@Component({
  selector: 'lecture-class-member',
  templateUrl: './class-member.component.html',
  styleUrls: ['./class-member.component.css']
})
export class ClassMemberComponent {
  displayedColumns: string[] = ['id', 'name', 'email', 'join'];
  dataSource!: MatTableDataSource<ClassStudent>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  classStudents: ClassStudent[] = [];
  class: Class = new Class();
  id!: number;

  constructor(
    private manageClassService: LectureClassService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getClass();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private getClass() {
    this.id = this.route.snapshot.params['id'];
    console.log(this.id)
    this.class = new Class();
    this.manageClassService.getClassById(this.id).subscribe(data => {
      this.class = data;
    })
    this.classStudents = []
    this.manageClassService.getMemberbyClass(this.id).subscribe(data => {
      this.classStudents = data;
      console.log(this.classStudents)
      this.dataSource = new MatTableDataSource(this.classStudents);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

}
