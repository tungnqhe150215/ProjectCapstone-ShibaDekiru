import {Component, OnInit} from '@angular/core';
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {TestSection} from "../../../core/models/test-section";

@Component({
  selector: 'app-test-section',
  templateUrl: './test-section.component.html',
  styleUrls: ['./test-section.component.css']
})
export class TestSectionComponent implements OnInit{

  user: any;
  expandedElement: TestSection | null = null;
  selectedTabIndex = 0;

  constructor(
    private sessionStorageService: SessionStorageService
  ) {
  }

  ngOnInit(): void {
    this.user = this.sessionStorageService.getJsonData("auth-user");
  }

  switchTab(tabIndex: number) {
    this.selectedTabIndex = tabIndex;
    this.expandedElement = null
    // Thực hiện các xử lý khác khi bạn chuyển tab
  }
}

