import {Component, Input} from '@angular/core';
import {TestSection} from "../../../core/models/test-section";
import {data} from "autoprefixer";

@Component({
  selector: 'app-student-listening-test',
  templateUrl: './student-listening-test.component.html',
  styleUrls: ['./student-listening-test.component.css']
})
export class StudentListeningTestComponent {

  @Input() sectionList: TestSection[] = [];
  selectedSection: TestSection | undefined;

  topics = ['Chủ đề 1', 'Chủ đề 2', 'Chủ đề 3', 'Chủ đề 4'];


  goToSection(sectionId: number) {
    console.log(sectionId)
    this.selectedSection = new TestSection();
    this.selectedSection = this.sectionList.find(data => data.sectionId === sectionId) as TestSection;
    console.log(this.selectedSection)
  }

}
