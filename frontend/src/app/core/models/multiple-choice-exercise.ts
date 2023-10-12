import {Exercise} from "./exercise";

export class MultipleChoiceExercise {
    id?:number
    exercise?:Exercise
    question?:String
    first_choice?:String
    second_choice?:String
    third_choice?:String
    fourth_choice?:String
    correct_answer?:String
}
