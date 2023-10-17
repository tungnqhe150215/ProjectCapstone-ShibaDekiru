import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListListeningComponent } from './list-listening.component';

describe('ListListeningComponent', () => {
  let component: ListListeningComponent;
  let fixture: ComponentFixture<ListListeningComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListListeningComponent]
    });
    fixture = TestBed.createComponent(ListListeningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
