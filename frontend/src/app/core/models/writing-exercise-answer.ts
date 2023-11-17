import {Student} from "./student";
import {WritingExercise} from "./writing-exercise";

export class WritingExerciseAnswer {
  writingExerciseAnswerId!: number
  answer!: string
  comment!: string
  mark!: number
  student!: Student
  writingExercise!: WritingExercise
}
