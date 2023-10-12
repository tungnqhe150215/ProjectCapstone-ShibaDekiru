import {Role} from "./role";

export class UserAccount {
  id?:number
  role?:Role
  nick_name?:String
  username?:String
  password?:String
  email?:String
  reset_code?:String
  is_banned?:Boolean
}
