
import {Student} from "./student";
import {TestSection} from "./test-section";
import {TestAssign} from "./test-assign";

export class TestResult {
    id!:number
    testSection!:TestSection
    student!:Student
    result!:number
    doneTime!:Date
    numberOfQuestion!:number
    classTestAssign!:TestAssign
}
