import {Test} from "./test";
import {Student} from "./student";
import {Time} from "@angular/common";

export class TestResult {
    id?:number
    test?:Test
    student?:Student
    result?:number
    done_time?:Time
}
