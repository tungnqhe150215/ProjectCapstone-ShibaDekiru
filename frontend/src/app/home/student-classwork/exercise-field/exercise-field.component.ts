import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise} from "../../../core/models/exercise";
import {ActivatedRoute} from "@angular/router";
import {ClassworkAnswerService} from "../classwork-answer.service";
import {WritingExercise} from "../../../core/models/writing-exercise";
import {StudentClassworkService} from "../../class/student-classwork.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-exercise-field',
  templateUrl: './exercise-field.component.html',
  styleUrls: ['./exercise-field.component.css']
})
export class ExerciseFieldComponent implements OnInit, OnChanges {
  @Input() exercise: Exercise = new Exercise()
  writingExercise: WritingExercise[] = []

  constructor(private route: ActivatedRoute,
              private answer: ClassworkAnswerService,
              private classwork: StudentClassworkService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
       if (changes['exercise'] && changes['exercise'].previousValue !== changes['exercise'].currentValue){
         this.getWritingExercise()
       }
  }

  ngOnInit(): void {
    this.getWritingExercise()
  }

  getWritingExercise(){
    this.classwork.getWritingExerciseByExercise(this.exercise.exerciseId).subscribe(data => {
      this.writingExercise = data
    })
  }
}
