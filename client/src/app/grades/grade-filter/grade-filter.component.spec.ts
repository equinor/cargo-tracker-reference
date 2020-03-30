import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeFilterComponent } from './grade-filter.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { StoSlideToggleModule } from '@ngx-stoui/form';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('GradeFilterComponent', () => {
  let component: GradeFilterComponent;
  let fixture: ComponentFixture<GradeFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        imports: [ ReactiveFormsModule, MatSelectModule, MatFormFieldModule, StoSlideToggleModule, NoopAnimationsModule ],
        declarations: [ GradeFilterComponent ],
        schemas: [ NO_ERRORS_SCHEMA ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeFilterComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit upload', () => {
    const ev = {
      currentTarget: {
        value: null,
        files: [ 'A Very Valid File' ]
      }
    };
    const spy = spyOn(component.uploadSheet, 'emit');
    component.upload(ev as any);
    expect(spy).toHaveBeenCalledWith(ev.currentTarget.files[ 0 ]);
  });
});
