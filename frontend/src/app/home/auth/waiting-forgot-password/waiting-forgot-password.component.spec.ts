import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaitingForgotPasswordComponent } from './waiting-forgot-password.component';

describe('WaitingForgotPasswordComponent', () => {
  let component: WaitingForgotPasswordComponent;
  let fixture: ComponentFixture<WaitingForgotPasswordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaitingForgotPasswordComponent]
    });
    fixture = TestBed.createComponent(WaitingForgotPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
