import {Listening} from "./listening";

export class ListeningQuestion {
    listeningQuestionId!:number
    listening?:Listening
    question!:String
    firstChoice!:String
    secondChoice!:String
    thirdChoice!:String
    correctAnswer!:String
}
