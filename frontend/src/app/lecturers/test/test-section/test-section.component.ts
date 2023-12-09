import {Component, OnInit} from '@angular/core';
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {TestSection} from "../../../core/models/test-section";
import {Test} from "../../../core/models/test";
import {ActivatedRoute, Route, Routes} from "@angular/router";
import {LectureManageTestService} from "../lecture-manage-test.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-test-section',
  templateUrl: './test-section.component.html',
  styleUrls: ['./test-section.component.css']
})
export class TestSectionComponent implements OnInit{

  user: any;
  test: Test = new Test();
  expandedElement: TestSection | null = null;
  selectedTabIndex = 0;
  testId !: number

  constructor(
    private sessionStorageService: SessionStorageService,
    private route: ActivatedRoute,
    private testService : LectureManageTestService
  ) {
  }

  ngOnInit(): void {
    this.testId = this.route.snapshot.params['id'];
    this.testService.getTestById(this.testId).subscribe(data => {
      this.test = data
    })
    this.user = this.sessionStorageService.getJsonData("auth-user");
  }

  switchTab(tabIndex: number) {
    this.selectedTabIndex = tabIndex;
    this.expandedElement = null
    // Thực hiện các xử lý khác khi bạn chuyển tab
  }
}

