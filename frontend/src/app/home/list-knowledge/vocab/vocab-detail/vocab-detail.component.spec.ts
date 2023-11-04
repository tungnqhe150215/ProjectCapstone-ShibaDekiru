import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VocabDetailComponent } from './vocab-detail.component';

describe('VocabDetailComponent', () => {
  let component: VocabDetailComponent;
  let fixture: ComponentFixture<VocabDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VocabDetailComponent]
    });
    fixture = TestBed.createComponent(VocabDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
