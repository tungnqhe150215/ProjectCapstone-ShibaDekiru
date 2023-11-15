import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../core/models/exercise";
import {ActivatedRoute} from "@angular/router";
import {StudentClassworkService} from "../class/student-classwork.service";
import {ClassworkAnswerService} from "./classwork-answer.service";
import {data} from "autoprefixer";
import {ClassWork} from "../../core/models/class-work";

@Component({
  selector: 'app-student-classwork',
  templateUrl: './student-classwork.component.html',
  styleUrls: ['./student-classwork.component.css','../home-style.css']
})
export class StudentClassworkComponent implements OnInit,OnDestroy{

  exerciseList: Exercise[] = []
  classwork: ClassWork = new ClassWork()
  classworkId!: number
  selectedExercise: Exercise | undefined

  constructor(private route: ActivatedRoute,
              private classworkService: StudentClassworkService,
              private answer: ClassworkAnswerService) {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.getExercise()
  }

  getExercise(){
    this.classworkId = this.route.snapshot.params['classworkId'];
    this.classworkService.getClassWorkById(this.classworkId).subscribe(data => {
      this.classwork = data
    })
    this.classworkService.getExerciseByClasswork(this.classworkId).subscribe(data =>{
      this.exerciseList = data
    })
  }

  goToExercise(exerciseId: number) {
    this.selectedExercise = new Exercise();
    this.selectedExercise = this.exerciseList.find(data => data.exerciseId === exerciseId) as Exercise
  }
}
