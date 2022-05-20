import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStageByEnseignantComponent } from './list-stage-by-enseignant.component';

describe('ListStageByEnseignantComponent', () => {
  let component: ListStageByEnseignantComponent;
  let fixture: ComponentFixture<ListStageByEnseignantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStageByEnseignantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStageByEnseignantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
