import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportFileEnseignantComponent } from './import-file-enseignant.component';

describe('ImportFileEnseignantComponent', () => {
  let component: ImportFileEnseignantComponent;
  let fixture: ComponentFixture<ImportFileEnseignantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImportFileEnseignantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportFileEnseignantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
