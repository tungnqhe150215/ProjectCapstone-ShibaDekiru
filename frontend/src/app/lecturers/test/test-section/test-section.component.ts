import {Component, OnInit} from '@angular/core';
import {SessionStorageService} from "../../../shared/services/session-storage.service";

@Component({
  selector: 'app-test-section',
  templateUrl: './test-section.component.html',
  styleUrls: ['./test-section.component.css']
})
export class TestSectionComponent implements OnInit{
  user: any;

  constructor(
    private sessionStorageService: SessionStorageService
  ) {
  }

  ngOnInit(): void {
    this.user = this.sessionStorageService.getJsonData("auth-user");
  }

}

