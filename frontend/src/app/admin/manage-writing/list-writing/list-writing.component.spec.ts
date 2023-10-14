import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListWritingComponent } from './list-writing.component';

describe('ListWritingComponent', () => {
  let component: ListWritingComponent;
  let fixture: ComponentFixture<ListWritingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListWritingComponent]
    });
    fixture = TestBed.createComponent(ListWritingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
