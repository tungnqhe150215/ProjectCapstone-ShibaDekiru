import {Component, Input} from '@angular/core';
import {TestSection} from "../../../core/models/test-section";

@Component({
  selector: 'app-student-reading-test',
  templateUrl: './student-reading-test.component.html',
  styleUrls: ['./student-reading-test.component.css']
})
export class StudentReadingTestComponent {


  @Input() sectionList: TestSection[] = [];
  selectedSection: TestSection | undefined;


  goToSection(sectionId: number) {
    console.log(sectionId)
    this.selectedSection = new TestSection();
    this.selectedSection = this.sectionList.find(data => data.sectionId === sectionId) as TestSection;
    console.log(this.selectedSection)
  }

}
