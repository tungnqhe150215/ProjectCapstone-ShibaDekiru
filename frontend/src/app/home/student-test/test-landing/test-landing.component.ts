import {Component, OnInit} from '@angular/core';
import {StudentTestService} from "../student-test.service";
import {Test} from "../../../core/models/test";
import {ActivatedRoute, Router} from "@angular/router";
import {data} from "autoprefixer";

@Component({
  selector: 'app-test-landing',
  templateUrl: './test-landing.component.html',
  styleUrls: ['./test-landing.component.css','../../home-style.css']
})
export class TestLandingComponent implements OnInit{

  test: Test = new Test();
  id!:number
  classId!:number
  constructor(private studentTestService: StudentTestService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getTest()
  }

  getTest() {
    this.classId = this.route.snapshot.params['classId'];
    this.id = this.route.snapshot.params['testId'];
    this.studentTestService.getTestById(this.id).subscribe( data => {
      this.test = data;
    })
  }

  startTest() {
    this.router.navigate(['/c/'+this.classId+'/t/'+this.id+'/do']);
  }
}
