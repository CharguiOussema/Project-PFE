import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemoireEnseignantComponent } from './memoire-enseignant.component';

describe('MemoireEnseignantComponent', () => {
  let component: MemoireEnseignantComponent;
  let fixture: ComponentFixture<MemoireEnseignantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemoireEnseignantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemoireEnseignantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
