import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStagePFEComponent } from './add-stage-pfe.component';

describe('AddStagePFEComponent', () => {
  let component: AddStagePFEComponent;
  let fixture: ComponentFixture<AddStagePFEComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddStagePFEComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddStagePFEComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
