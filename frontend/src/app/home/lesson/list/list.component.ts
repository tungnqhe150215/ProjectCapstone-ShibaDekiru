import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-lesson-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  constructor() {
  }

  lessons = [
    {id: 1, name: 'bai 1'},
    {id: 2, name: 'bai 2'},
    {id: 3, name: 'bai 3'},
    {id: 4, name: 'bai 4'},
    {id: 5, name: 'bai 5'},
    {id: 6, name: 'bai 6'},
    {id: 7, name: 'bai 7'},
    {id: 8, name: 'bai 8'},
    {id: 9, name: 'bai 9'},
  ]

  ngOnInit(): void {
  }
}
