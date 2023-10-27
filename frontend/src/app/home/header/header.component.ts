import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';

@Component({
  selector: 'home-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css','../home-style.css']
})
export class HeaderComponent implements AfterViewInit{

  @ViewChild('header') elementView!: ElementRef<HTMLInputElement>;

  contentHeight !: number;
  scrollNumber !:number;

  constructor() {
  }

  ngAfterViewInit(): void {

  }

}
