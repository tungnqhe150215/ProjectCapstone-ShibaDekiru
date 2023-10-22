import {UserAccount} from "./user-account";

export class Lecture {
  lectureId!:number
  user?:UserAccount
  first_name?:String
  last_name?:String
  email?:String
  gender?:String
  avatar?:String
}
