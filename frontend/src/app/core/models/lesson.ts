import {Book} from "./book";

export class Lesson {
    lessonId!:number
    bookId!:number
    name?:String
    content?:String
    created_at?:Date
    status?:String
    image?:String
}
