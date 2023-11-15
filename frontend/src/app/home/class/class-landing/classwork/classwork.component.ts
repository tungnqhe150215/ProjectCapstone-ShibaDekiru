import {Component, OnInit} from '@angular/core';
import {ClassWork} from "../../../../core/models/class-work";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassworkService} from "../../student-classwork.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-classwork',
  templateUrl: './classwork.component.html',
  styleUrls: ['./classwork.component.css']
})
export class ClassworkComponent implements OnInit{

  classWork: ClassWork[] = [];
  classId!: number

  constructor(private route: ActivatedRoute,
              private router: Router,
              private studentClasswork: StudentClassworkService) {
  }

  ngOnInit(): void {
    this.getClassWork()
  }

  private getClassWork() {
    this.classId = this.route.snapshot.params['id'];
    this.studentClasswork.getClassWorkByClass(this.classId).subscribe(data => {
      this.classWork = data
    })
  }

  goToClasswork(classWorkId: number) {
    this.router.navigate(['c',this.classId,'cw',classWorkId,'do'])
  }

}
