
import {Student} from "./student";
import {TestSection} from "./test-section";

export class TestResult {
    id!:number
    testSection!:TestSection
    student!:Student
    result!:number
    doneTime!:Date
    numberOfQuestion!:number
}
