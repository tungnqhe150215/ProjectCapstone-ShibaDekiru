import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Class} from "../../../../core/models/class";
import {StudentClassService} from "../../student-class.service";
import {ClassTestService} from "../../class-test.service";
import {TestAssign} from "../../../../core/models/test-assign";
import {data} from "autoprefixer";

@Component({
  selector: 'app-class-test',
  templateUrl: './class-test.component.html',
  styleUrls: ['./class-test.component.css']
})
export class ClassTestComponent implements OnInit{

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

  gotoTest(number: number) {
    this.router.navigate(['c/'+this.classNow.classId+'/t/'+number+'/landing'])
  }

  checkExpiredTest(test:TestAssign){
    return new Date(test.accessExpirationDate) > new Date();
  }
}
