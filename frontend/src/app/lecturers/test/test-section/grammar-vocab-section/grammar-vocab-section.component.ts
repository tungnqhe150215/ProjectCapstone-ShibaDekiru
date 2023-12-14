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
  selector: 'app-grammar-vocab-section',
  templateUrl: './grammar-vocab-section.component.html',
  styleUrls: ['./grammar-vocab-section.component.css'],
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
export class GrammarVocabSectionComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private testSectionService: LectureTestSectionService,
    private testService: LectureManageTestService,
    public dialog: MatDialog
  ) {
  }

  dataSource:TestSection[] = [];
  columnsToDisplay = ['id', 'name','action'];
  test: Test = new Test()
  id!:number

  ngOnInit(): void {
  this.getGrammarSection()
  }

  private getGrammarSection(){
    this.id = this.route.snapshot.params['id'];
    this.testService.getTestById(this.id).subscribe(data => {
      this.test = data
      console.log(data)
    });
    this.dataSource = []
    this.testSectionService.getTestSectionByTestAndType(this.id,"GRAMMAR_VOCAB").subscribe(data => {
      this.dataSource = data;
      console.log(data)
    })
    // return this.courseService.getCourseList();
  }

  openDeleteGrammarSectionDialog(id:number){
    this.dialog.open(GrammarSectionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getGrammarSection()});
  }

  openCreateGrammarSectionDialog(id:number){
    this.dialog.open(GrammarSectionCreateDialog,{
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getGrammarSection()});
  }

  openUpdateGrammarSectionDialog(id:number){
    this.dialog.open(GrammarSectionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(data => { if (data) this.getGrammarSection()});
  }

  getGrammarSectionDetail(id:number){
    this.router.navigate(['lecturer/test/section',id]);
  }
}

@Component({
  selector: 'app-grammar-section-delete-dialog',
  templateUrl: 'grammar-section-delete-dialog.html',
  styleUrls: ['./grammar-vocab-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class GrammarSectionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<GrammarSectionDeleteDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteGrammarSection(id:number){
    this.testSectionService.deleteTestSection(id).subscribe(data => {
      this.dialogRef.close(data);
    })
    this._snackBar.open('Xóa thành công!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-grammar-section-create-dialog',
  templateUrl: 'grammar-section-create-dialog.html',
  styleUrls: ['./grammar-vocab-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class GrammarSectionCreateDialog {

  section:TestSection =  new TestSection();

  constructor(
    public dialogRef: MatDialogRef<GrammarSectionCreateDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createGrammarSection(){
    this.section.sectionType = "GRAMMAR_VOCAB"
    console.log(this.section)
    this.testSectionService.createTestSection(this.data,this.section).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Phần mới đã được thêm!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-grammar-section-update-dialog',
  templateUrl: 'grammar-section-update-dialog.html',
  styleUrls: ['./grammar-vocab-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class GrammarSectionUpdateDialog implements OnInit{

  section: TestSection = new TestSection();

  constructor(
    public dialogRef: MatDialogRef<GrammarSectionUpdateDialog>,
    private testSectionService:LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.testSectionService.getTestSectionById(this.data).subscribe(e => {
      this.section = e
    })
  }

  updateGrammarSection(){
    console.log(this.section)
    this.testSectionService.updateTestSection(this.data,this.section).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Cập nhật thành công!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
