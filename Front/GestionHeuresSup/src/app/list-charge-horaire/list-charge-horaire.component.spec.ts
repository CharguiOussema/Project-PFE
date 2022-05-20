import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListChargeHoraireComponent } from './list-charge-horaire.component';

describe('ListChargeHoraireComponent', () => {
  let component: ListChargeHoraireComponent;
  let fixture: ComponentFixture<ListChargeHoraireComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListChargeHoraireComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListChargeHoraireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
