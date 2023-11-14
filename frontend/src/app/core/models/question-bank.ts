import {TestSection} from "./test-section";

export class QuestionBank {
    questionBankId!:number
    testSection!:TestSection
    question!:string
    firstChoice!:string
    secondChoice!:string
    thirdChoice!:string
    fourthChoice!:string
    correctAnswer!:string
}
