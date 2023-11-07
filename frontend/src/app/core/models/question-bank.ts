import {TestSection} from "./test-section";

export class QuestionBank {
    questionBankId!:number
    testSection!:TestSection
    question!:String
    firstChoice!:String
    secondChoice!:String
    thirdChoice!:String
    fourthChoice!:String
    correctAnswer!:String
}
