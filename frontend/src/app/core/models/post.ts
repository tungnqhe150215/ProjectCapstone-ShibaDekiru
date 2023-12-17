import {Lecture} from "./lecture";

export class Post {
  postId!:number
  LecturesDto?:Lecture
  postContent?:String
  description?:String
  createdAt?:Date
  image?:String
  isEnabled?:boolean
  lectureId?:number
}
