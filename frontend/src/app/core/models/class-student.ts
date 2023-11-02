import {Class} from "./class";
import {Student} from "./student";

export class ClassStudent {
    classStudentId!:number
    class!:Class
    student!:Student
    joinedAt!:Date
}
