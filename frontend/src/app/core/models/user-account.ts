import {Role} from "./role";

export class UserAccount {
  userAccountId!:number
  roleId!:number
  firstName!:String
  lastName!:String
  isActive!:Boolean
  nickName!:String
  userName?:String
  password?:String
  email?:String
  memberId!:String
  resetCode?:String
  isBanned!:Boolean
  role!:Role
}
