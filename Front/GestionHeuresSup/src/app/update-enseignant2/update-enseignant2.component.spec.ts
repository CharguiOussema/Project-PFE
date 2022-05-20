import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEnseignant2Component } from './update-enseignant2.component';

describe('UpdateEnseignant2Component', () => {
  let component: UpdateEnseignant2Component;
  let fixture: ComponentFixture<UpdateEnseignant2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateEnseignant2Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateEnseignant2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
