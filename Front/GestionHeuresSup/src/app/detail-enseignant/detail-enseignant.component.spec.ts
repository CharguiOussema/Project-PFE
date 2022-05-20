import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailEnseignantComponent } from './detail-enseignant.component';

describe('DetailEnseignantComponent', () => {
  let component: DetailEnseignantComponent;
  let fixture: ComponentFixture<DetailEnseignantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailEnseignantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailEnseignantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
