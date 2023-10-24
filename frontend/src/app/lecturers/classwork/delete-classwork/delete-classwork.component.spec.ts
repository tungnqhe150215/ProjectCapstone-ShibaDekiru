import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteClassworkComponent } from './delete-classwork.component';

describe('DeleteClassworkComponent', () => {
  let component: DeleteClassworkComponent;
  let fixture: ComponentFixture<DeleteClassworkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteClassworkComponent]
    });
    fixture = TestBed.createComponent(DeleteClassworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
