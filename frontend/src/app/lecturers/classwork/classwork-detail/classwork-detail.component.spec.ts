import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassworkDetailComponent } from './classwork-detail.component';

describe('ClassworkDetailComponent', () => {
  let component: ClassworkDetailComponent;
  let fixture: ComponentFixture<ClassworkDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassworkDetailComponent]
    });
    fixture = TestBed.createComponent(ClassworkDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
