import {Book} from "./book";

export class Lesson {
    lessonId!:number
    bookId!:number
    name?:String
    content?:String
    createdAt?:Date
    status?:String
    image?:String
}
