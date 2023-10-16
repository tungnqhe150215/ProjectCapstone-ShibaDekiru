import {Lecture} from "./lecture";

export class Post {
  postId?:number
  LecturesDto?:Lecture
  postContent?:String
  description?:String
  createdAt?:Date
  isEnabled?:boolean
}
