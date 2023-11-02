import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassLandingComponent } from './class-landing.component';

describe('ClassLandingComponent', () => {
  let component: ClassLandingComponent;
  let fixture: ComponentFixture<ClassLandingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassLandingComponent]
    });
    fixture = TestBed.createComponent(ClassLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
