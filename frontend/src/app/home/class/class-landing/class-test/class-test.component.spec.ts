import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassTestComponent } from './class-test.component';

describe('ClassTestComponent', () => {
  let component: ClassTestComponent;
  let fixture: ComponentFixture<ClassTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassTestComponent]
    });
    fixture = TestBed.createComponent(ClassTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
