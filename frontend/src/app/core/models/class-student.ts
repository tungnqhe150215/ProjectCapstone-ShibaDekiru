import {Class} from "./class";
import {Student} from "./student";

export class ClassStudent {
    classStudentId!:number
    belongClass!:Class
    student!:Student
    joinedAt!:Date
}
