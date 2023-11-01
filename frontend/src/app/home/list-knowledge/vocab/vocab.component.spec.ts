import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VocabComponent } from './vocab.component';

describe('VocabComponent', () => {
  let component: VocabComponent;
  let fixture: ComponentFixture<VocabComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VocabComponent]
    });
    fixture = TestBed.createComponent(VocabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
