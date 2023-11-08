import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassworkComponent } from './classwork.component';

describe('ClassworkComponent', () => {
  let component: ClassworkComponent;
  let fixture: ComponentFixture<ClassworkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassworkComponent]
    });
    fixture = TestBed.createComponent(ClassworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});