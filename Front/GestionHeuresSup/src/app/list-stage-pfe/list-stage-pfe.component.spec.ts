import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStagePFEComponent } from './list-stage-pfe.component';

describe('ListStagePFEComponent', () => {
  let component: ListStagePFEComponent;
  let fixture: ComponentFixture<ListStagePFEComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStagePFEComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStagePFEComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
