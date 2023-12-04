import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnthorizationComponent } from './unthorization.component';

describe('UnthorizationComponent', () => {
  let component: UnthorizationComponent;
  let fixture: ComponentFixture<UnthorizationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnthorizationComponent]
    });
    fixture = TestBed.createComponent(UnthorizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
