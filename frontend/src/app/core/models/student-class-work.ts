import {Student} from "./student";
import {ClassWork} from "./class-work";

export class StudentClassWork {
    id!:number
    student!:Student
    classWork!:ClassWork
    result!:number
    submitTime!:Date
}
