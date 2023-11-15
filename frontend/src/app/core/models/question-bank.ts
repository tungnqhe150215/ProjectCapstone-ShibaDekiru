import {TestSection} from "./test-section";

export class QuestionBank {
    questionBankId!:number
    section!:TestSection
    question!:string
    firstChoice!:string
    secondChoice!:string
    thirdChoice!:string
    fourthChoice!:string
    correctAnswer!:string
}
