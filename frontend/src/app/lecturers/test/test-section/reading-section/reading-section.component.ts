import {Component, Inject, Input, OnInit} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {NgFor, NgIf} from "@angular/common";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {ActivatedRoute, Router} from "@angular/router";
import {LectureTestSectionService} from "../../lecture-test-section.service";
import {Test} from "../../../../core/models/test";
import {LectureManageTestService} from "../../lecture-manage-test.service";
import {TestSection} from "../../../../core/models/test-section";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatSortModule} from "@angular/material/sort";


@Component({
  selector: 'app-reading-section',
  templateUrl: './reading-section.component.html',
  styleUrls: ['./reading-section.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
  standalone: true,
  imports: [MatTableModule, NgFor, MatButtonModule, NgIf, MatIconModule, MatSortModule],
})
export class ReadingSectionComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private testSectionService: LectureTestSectionService,
    private testService: LectureManageTestService,
    public dialog: MatDialog
  ) {
  }

  @Input() expandedElement: any | null;

  dataSource:TestSection[] = [];
  columnsToDisplay = ['id', 'name','action'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  test: Test = new Test()
  id!:number

  ngOnInit(): void {
    this.expandedElement = null;
    this.getReadingSection()
  }

  private getReadingSection(){
    this.id = this.route.snapshot.params['id'];
    this.testService.getTestById(this.id).subscribe(data => {
      this.test = data
      console.log(data)
    });
    this.dataSource = []
    this.testSectionService.getTestSectionByTestAndType(this.id,"READING").subscribe(data => {
      this.dataSource = data;
      console.log(data)
    })
    // return this.courseService.getCourseList();
  }

  openDeleteReadingSectionDialog(id:number){
    this.dialog.open(ReadingSectionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getReadingSection());
  }

  openCreateReadingSectionDialog(id:number){
    this.dialog.open(ReadingSectionCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getReadingSection());
  }

  openUpdateReadingSectionDialog(id:number){
    this.dialog.open(ReadingSectionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getReadingSection());
  }

  getReadingSectionDetail(id:number){
    this.router.navigate(['lecturer/test/section',id]);
  }
}

@Component({
  selector: 'app-reading-section-delete-dialog',
  templateUrl: 'reading-section-delete-dialog.html',
  styleUrls: ['./reading-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingSectionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ReadingSectionDeleteDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteReadingSection(id:number){
    this.testSectionService.deleteTestSection(id).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Deleted!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-reading-section-create-dialog',
  templateUrl: 'reading-section-create-dialog.html',
  styleUrls: ['./reading-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingSectionCreateDialog {

  section:TestSection =  new TestSection();

  constructor(
    public dialogRef: MatDialogRef<ReadingSectionCreateDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createReadingSection(){
    this.section.sectionType = "READING"
    console.log(this.section)
    this.testSectionService.createTestSection(this.data,this.section).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('New reading part added!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-reading-section-update-dialog',
  templateUrl: 'reading-section-update-dialog.html',
  styleUrls: ['./reading-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ReadingSectionUpdateDialog implements OnInit{

  section: TestSection = new TestSection();

  constructor(
    public dialogRef: MatDialogRef<ReadingSectionUpdateDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.testSectionService.getTestSectionById(this.data).subscribe(e => {
      this.section = e
    })
  }

  updateReadingSection(){
    console.log(this.section)
    this.testSectionService.updateTestSection(this.data,this.section).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('Reading Section part updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
