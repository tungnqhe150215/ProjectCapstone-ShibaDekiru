import {UserAccount} from "./user-account";

export class Comment {
    id?:number
    user?:UserAccount
    content?:String
    created_at?:Date
}
