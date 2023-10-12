import {Chat} from "./chat";

export class ChatMessage {
  id?:number
  chat?:Chat
  message_content?:String
  time?:Date
  is_deleted?:Boolean
}
