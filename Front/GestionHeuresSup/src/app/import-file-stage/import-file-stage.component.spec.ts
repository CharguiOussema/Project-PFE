import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportFileStageComponent } from './import-file-stage.component';

describe('ImportFileStageComponent', () => {
  let component: ImportFileStageComponent;
  let fixture: ComponentFixture<ImportFileStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImportFileStageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportFileStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
