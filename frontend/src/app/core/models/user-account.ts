import {Role} from "./role";

export class UserAccount {
  userAccountId!:number
  roleId!:number
  firstName!:String
  lastName!:String
  isActive!:Boolean
  nickName!:string
  userName?:String
  password?:String
  email?:String
  memberId!:String
  resetCode?:String
  isBanned!:Boolean
  isCreatedByAdmin!:Boolean
  role!:Role
}
