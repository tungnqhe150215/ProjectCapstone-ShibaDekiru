import {UserAccount} from "./user-account";

export class Comment {
    commentId!:number
    user!:UserAccount
    createdAt?:Date
    content?:String
    userAccountId!:number
    postId!:number
    nickName!:String
}
