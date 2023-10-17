import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeningDetailComponent } from './listening-detail.component';

describe('ListeningDetailComponent', () => {
  let component: ListeningDetailComponent;
  let fixture: ComponentFixture<ListeningDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListeningDetailComponent]
    });
    fixture = TestBed.createComponent(ListeningDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
