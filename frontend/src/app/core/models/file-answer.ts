import {Student} from "./student";
import {SubmitFileExercise} from "./submit-file-exercise";

export class FileAnswer{
 fileAnswerId!:number
 fileAnswer!:string
 mark!:number
 student!: Student
 submitFileExercise!: SubmitFileExercise;
}
