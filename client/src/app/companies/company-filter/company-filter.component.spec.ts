import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyFilterComponent } from './company-filter.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('CompanyFilterComponent', () => {
  let component: CompanyFilterComponent;
  let fixture: ComponentFixture<CompanyFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ CompanyFilterComponent ],
        schemas: [ NO_ERRORS_SCHEMA ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
