import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeFilterComponent } from './grade-filter.component';

describe('GradeFilterComponent', () => {
  let component: GradeFilterComponent;
  let fixture: ComponentFixture<GradeFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GradeFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
