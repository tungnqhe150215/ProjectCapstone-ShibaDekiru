import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {StudentClassService} from "../student-class.service";
import {Class} from "../../../core/models/class";
import {data} from "autoprefixer";

@Component({
  selector: 'app-class-landing',
  templateUrl: './class-landing.component.html',
  styleUrls: ['./class-landing.component.css', '../../home-style.css']
})
export class ClassLandingComponent implements OnInit {

  id!:number
  aClass: Class = new Class();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private studentClassService: StudentClassService) {
  }

  ngOnInit(): void {
    this.getClassInfo();
  }

  getClassInfo(){
    this.id = this.route.snapshot.params['id'];
    this.studentClassService.getClassById(this.id).subscribe(data => {
      this.aClass = data
    })
  }
}
