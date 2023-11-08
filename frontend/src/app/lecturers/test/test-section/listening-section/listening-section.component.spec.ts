import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeningSectionComponent } from './listening-section.component';

describe('ListeningSectionComponent', () => {
  let component: ListeningSectionComponent;
  let fixture: ComponentFixture<ListeningSectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListeningSectionComponent]
    });
    fixture = TestBed.createComponent(ListeningSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
