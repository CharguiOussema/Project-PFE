import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeMemoireComponent } from './liste-memoire.component';

describe('ListeMemoireComponent', () => {
  let component: ListeMemoireComponent;
  let fixture: ComponentFixture<ListeMemoireComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeMemoireComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeMemoireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
