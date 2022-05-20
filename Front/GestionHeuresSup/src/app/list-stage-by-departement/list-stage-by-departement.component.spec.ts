import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStageByDepartementComponent } from './list-stage-by-departement.component';

describe('ListStageByDepartementComponent', () => {
  let component: ListStageByDepartementComponent;
  let fixture: ComponentFixture<ListStageByDepartementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStageByDepartementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStageByDepartementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
