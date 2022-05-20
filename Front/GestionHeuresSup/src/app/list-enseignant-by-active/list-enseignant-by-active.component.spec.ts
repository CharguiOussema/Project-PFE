import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEnseignantByActiveComponent } from './list-enseignant-by-active.component';

describe('ListEnseignantByActiveComponent', () => {
  let component: ListEnseignantByActiveComponent;
  let fixture: ComponentFixture<ListEnseignantByActiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListEnseignantByActiveComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListEnseignantByActiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
