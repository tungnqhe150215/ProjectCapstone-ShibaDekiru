import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListKaiwaComponent } from './list-kaiwa.component';

describe('ListKaiwaComponent', () => {
  let component: ListKaiwaComponent;
  let fixture: ComponentFixture<ListKaiwaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListKaiwaComponent]
    });
    fixture = TestBed.createComponent(ListKaiwaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
