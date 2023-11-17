import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassTestResultComponent } from './class-test-result.component';

describe('ClassTestResultComponent', () => {
  let component: ClassTestResultComponent;
  let fixture: ComponentFixture<ClassTestResultComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassTestResultComponent]
    });
    fixture = TestBed.createComponent(ClassTestResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
