import {Component, OnInit} from '@angular/core';
import {TestResult} from "../../../core/models/test-result";
import {StudentTestResultService} from "../student-test-result.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-test-result',
  templateUrl: './test-result.component.html',
  styleUrls: ['./test-result.component.css', '../../home-style.css']
})
export class TestResultComponent implements OnInit {

  testResultList: TestResult[] = [];

  testId!: number

  classId!: number

  constructor(private studentResultService: StudentTestResultService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }


  getData(){
    this.testId = this.route.snapshot.params['testId'];
    this.classId = this.route.snapshot.params['classId'];
  }
  backToClass() {
    this.router.navigate(['class',this.classId]);
  }
}
