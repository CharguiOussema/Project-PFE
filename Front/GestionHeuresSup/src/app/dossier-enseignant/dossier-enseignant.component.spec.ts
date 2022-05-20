import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DossierEnseignantComponent } from './dossier-enseignant.component';

describe('DossierEnseignantComponent', () => {
  let component: DossierEnseignantComponent;
  let fixture: ComponentFixture<DossierEnseignantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DossierEnseignantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DossierEnseignantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
