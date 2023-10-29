import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExerciseComponent } from './list-exercise.component';

describe('ListExerciseComponent', () => {
  let component: ListExerciseComponent;
  let fixture: ComponentFixture<ListExerciseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListExerciseComponent]
    });
    fixture = TestBed.createComponent(ListExerciseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
