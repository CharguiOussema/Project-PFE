import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecapEnseignantsComponent } from './recap-enseignants.component';

describe('RecapEnseignantsComponent', () => {
  let component: RecapEnseignantsComponent;
  let fixture: ComponentFixture<RecapEnseignantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecapEnseignantsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecapEnseignantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
