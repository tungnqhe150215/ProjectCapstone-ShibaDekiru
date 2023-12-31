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
import {FileService} from "../../../../shared/services/file.service";
import {data} from "autoprefixer";
import {Drive} from "../../../../core/models/drive";
import {FilePreviewService} from "../../../../shared/services/file-preview.service";
import {SharedModule} from "../../../../shared/shared.module";
import {debounceTime, finalize, firstValueFrom, switchMap, tap, timer} from "rxjs";
import { NotificationService } from 'src/app/core/services/notification.service';


@Component({
  selector: 'app-listening-section',
  templateUrl: './listening-section.component.html',
  styleUrls: ['./listening-section.component.css'],
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
export class ListeningSectionComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private testSectionService: LectureTestSectionService,
    private testService: LectureManageTestService,
    public dialog: MatDialog
  ) {
  }

  @Input() expandedElement: any | null;

  dataSource: TestSection[] = [];
  columnsToDisplay = ['id', 'name', 'action'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  test: Test = new Test()
  id!: number

  ngOnInit(): void {
    this.expandedElement = null;
    this.getListeningSection()
  }

  private getListeningSection() {
    this.id = this.route.snapshot.params['id'];
    this.testService.getTestById(this.id).subscribe(data => {
      this.test = data
      console.log(data)
    });
    this.dataSource = []
    this.testSectionService.getTestSectionByTestAndType(this.id, "LISTENING").subscribe(data => {
      this.dataSource = data;
      console.log(data)
    })
    // return this.courseService.getCourseList();
  }

  openDeleteListeningSectionDialog(id: number) {
    this.dialog.open(ListeningSectionDeleteDialog, {
      data: id
    }).afterClosed().subscribe(data => {if(data) {this.getListeningSection()}});
  }

  openCreateListeningSectionDialog(id: number) {
    this.dialog.open(ListeningSectionCreateDialog, {
      data: id
    }).afterClosed().subscribe(data => {if(data) {this.getListeningSection()}});
  }

  openUpdateListeningSectionDialog(id: number) {
    this.dialog.open(ListeningSectionUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(data => {if(data) {this.getListeningSection()}});
  }

  getListeningSectionDetail(id: number) {
    this.router.navigate(['lecturer/test/section', id]);
  }
}

@Component({
  selector: 'app-listening-section-delete-dialog',
  templateUrl: 'listening-section-delete-dialog.html',
  styleUrls: ['./listening-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ListeningSectionDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ListeningSectionDeleteDialog>,
    private testSectionService: LectureTestSectionService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {
  }

  deleteListeningSection(id: number) {
    this.testSectionService.deleteTestSection(id).subscribe(data => {
      this.dialogRef.close(data);
    })
    this.nofiService.openSnackBar('Đã xóa phần nghe!!');
    // this._snackBar.open('Đã xóa phần nghe!!', 'Đóng', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-listening-section-create-dialog',
  templateUrl: 'listening-section-create-dialog.html',
  styleUrls: ['./listening-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule,NgIf, SharedModule],
})
export class ListeningSectionCreateDialog {

  section: TestSection = new TestSection();
  file!: File;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ListeningSectionCreateDialog>,
    private testSectionService: LectureTestSectionService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {
  }

  createListeningSection() {
    if (this.file) {
      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.section.sectionType = "LISTENING"
        this.section.sectionAttach = this.drive.fileId;
        console.log(this.section)
        this.testSectionService.createTestSection(this.data, this.section).subscribe(data => {
          console.log(data)
          this.dialogRef.close(data)
        })
      })
    } else {
      this.section.sectionType = "LISTENING"
      this.testSectionService.createTestSection(this.data, this.section).subscribe(data => {
        console.log(data)
        this.dialogRef.close(data);
      })
    }
    this.nofiService.openSnackBar('Phần nghe mới đã được thêm!!');
    // this._snackBar.open('Phần nghe mới đã được thêm!!', 'Đóng', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}

@Component({
  selector: 'app-listening-section-update-dialog',
  templateUrl: 'listening-section-update-dialog.html',
  styleUrls: ['./listening-section.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, NgIf, SharedModule],
})
export class ListeningSectionUpdateDialog implements OnInit {

  section: TestSection = new TestSection();
  file!: File;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<ListeningSectionUpdateDialog>,
    private testSectionService: LectureTestSectionService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.testSectionService.getTestSectionById(this.data).subscribe(e => {
      this.section = e
    })
  }

 updateListeningSection() {
    if (this.file) {
      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.section.sectionAttach = this.drive.fileId;
        console.log(this.section)
        this.testSectionService.updateTestSection(this.data, this.section).subscribe(data => {
          console.log(data)
          this.dialogRef.close(data);
        })
      })
    } else {
      this.section.sectionAttach = ''
      this.testSectionService.updateTestSection(this.data, this.section).subscribe(data => {
        console.log(data)
        this.dialogRef.close(data);
      })
    }
    this.nofiService.openSnackBar('Phần nghe đã được cập nhật!!');
    // this._snackBar.open('Phần nghe đã được cập nhật!!', 'Đóng', {
    //   horizontalPosition: 'center',
    //   verticalPosition: 'top',
    // });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null && this.file.name.length > 0) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}
