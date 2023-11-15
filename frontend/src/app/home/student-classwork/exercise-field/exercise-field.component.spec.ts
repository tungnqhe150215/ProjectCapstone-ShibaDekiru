import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExerciseFieldComponent } from './exercise-field.component';

describe('ExerciseFieldComponent', () => {
  let component: ExerciseFieldComponent;
  let fixture: ComponentFixture<ExerciseFieldComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciseFieldComponent]
    });
    fixture = TestBed.createComponent(ExerciseFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
