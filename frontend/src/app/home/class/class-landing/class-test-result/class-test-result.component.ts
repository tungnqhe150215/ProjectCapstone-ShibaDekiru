import {Component, OnInit} from '@angular/core';
import {Class} from "../../../../core/models/class";
import {TestAssign} from "../../../../core/models/test-assign";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassService} from "../../student-class.service";
import {ClassTestService} from "../../class-test.service";

@Component({
  selector: 'app-class-test-result',
  templateUrl: './class-test-result.component.html',
  styleUrls: ['./class-test-result.component.css']
})
export class ClassTestResultComponent implements OnInit{

  classNow: Class = new Class();

  listTestAssign: TestAssign[] = [];

  classId!: number;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private studentClass: StudentClassService,
              private classTest: ClassTestService) {

  }

  ngOnInit(): void {
    this.getListTest()
  }

  getListTest(){
    this.classId = this.route.snapshot.params['id'];
    this.studentClass.getClassById(this.classId).subscribe(data => {
      this.classNow = data
      console.log(data)
    })
    this.classTest.getTestByClass(this.classId).subscribe(data => {
      this.listTestAssign = data
      console.log(data)
    })
  }
}
