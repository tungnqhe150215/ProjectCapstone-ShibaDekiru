import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-lesson-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css'],
})
export class SectionComponent implements OnInit {
  constructor() {
  }

  sectionDetail = [
    {
      vocabulary: 'ABC',
      chineseCharacters: 'ABC',
      pronounce: 'ABC',
      means: 'ABC',
    },
    {
      vocabulary: 'ABC',
      chineseCharacters: 'ABC',
      pronounce: 'ABC',
      means: 'ABC',
    },
    {
      vocabulary: 'ABC',
      chineseCharacters: 'ABC',
      pronounce: 'ABC',
      means: 'ABC',
    },
  ]

  ngOnInit(): void {
  }
}
