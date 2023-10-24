import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateClassworkComponent } from './update-classwork.component';

describe('UpdateClassworkComponent', () => {
  let component: UpdateClassworkComponent;
  let fixture: ComponentFixture<UpdateClassworkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateClassworkComponent]
    });
    fixture = TestBed.createComponent(UpdateClassworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
