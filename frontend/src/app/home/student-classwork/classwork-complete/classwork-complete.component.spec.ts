import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassworkCompleteComponent } from './classwork-complete.component';

describe('ClassworkCompleteComponent', () => {
  let component: ClassworkCompleteComponent;
  let fixture: ComponentFixture<ClassworkCompleteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassworkCompleteComponent]
    });
    fixture = TestBed.createComponent(ClassworkCompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
