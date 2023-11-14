import {Component, Input} from '@angular/core';
import {TestSection} from "../../../core/models/test-section";

@Component({
  selector: 'app-student-grammar-vocab-test',
  templateUrl: './student-grammar-vocab-test.component.html',
  styleUrls: ['./student-grammar-vocab-test.component.css']
})
export class StudentGrammarVocabTestComponent {


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
