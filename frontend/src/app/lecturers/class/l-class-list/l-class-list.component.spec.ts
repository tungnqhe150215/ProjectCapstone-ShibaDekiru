import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LClassListComponent } from './l-class-list.component';

describe('LClassListComponent', () => {
  let component: LClassListComponent;
  let fixture: ComponentFixture<LClassListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LClassListComponent]
    });
    fixture = TestBed.createComponent(LClassListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
