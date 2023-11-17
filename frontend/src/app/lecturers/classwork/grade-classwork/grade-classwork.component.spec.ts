import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeClassworkComponent } from './grade-classwork.component';

describe('GradeClassworkComponent', () => {
  let component: GradeClassworkComponent;
  let fixture: ComponentFixture<GradeClassworkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GradeClassworkComponent]
    });
    fixture = TestBed.createComponent(GradeClassworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
