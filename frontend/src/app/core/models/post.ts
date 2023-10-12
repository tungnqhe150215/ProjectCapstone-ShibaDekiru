import {Lecture} from "./lecture";

export class Post {
  id?:number
  lecture?:Lecture
  post_content?:String
  description?:String
  created_at?:Date
  status?:String
}
