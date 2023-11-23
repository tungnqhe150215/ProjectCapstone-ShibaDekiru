import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAllLessonComponent } from './list-all-lesson.component';

describe('ListAllLessonComponent', () => {
  let component: ListAllLessonComponent;
  let fixture: ComponentFixture<ListAllLessonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListAllLessonComponent]
    });
    fixture = TestBed.createComponent(ListAllLessonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
