import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateStagePFEComponent } from './update-stage-pfe.component';

describe('UpdateStagePFEComponent', () => {
  let component: UpdateStagePFEComponent;
  let fixture: ComponentFixture<UpdateStagePFEComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateStagePFEComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateStagePFEComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
