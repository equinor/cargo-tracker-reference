import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyListComponent } from './company-list.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Company } from '../../shared/models/company';
import { MatCheckboxChange } from '@angular/material';

describe('CompanyListComponent', () => {
  let component: CompanyListComponent;
  let fixture: ComponentFixture<CompanyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ CompanyListComponent ],
        schemas: [ NO_ERRORS_SCHEMA ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should get cancelled as class if company is cancelled', () => {
    const c = {} as Company;
    c.id = '1';
    c.cancelled = true;
    expect(component.rowClass(c)).toEqual('cancelled');
    c.cancelled = false;
    expect(component.rowClass(c)).toEqual('');
  });

  it('should trigger emit on verify emitter when verifiedChange is called', () => {
    const spy = spyOn(component.verify, 'emit');
    component.editing = {};
    const c = {} as Company;
    c.verified = false;
    c.id = '1';
    const ev = new MatCheckboxChange();
    ev.checked = true;
    component.verifiedChange(ev, c);
    expect(spy).toHaveBeenCalled();
  });

  describe('onValueChange', () => {
    let c: Company;
    beforeEach(() => {

      component.editing = {};
      c = {} as Company;
      c.id = '1';
      c.name = 'A Company';
    });

    it('should update component.editing when value changes', () => {
      expect(component.editing[ c.id ]).toBeUndefined();
      component.onValueChange('Another company', c);
      expect(component.editing[ c.id ]).toBeDefined();
    });

    it('should emit on cancel on keydown.escape', () => {
      const spy = spyOn(component.cancel, 'emit');
      const ev = {
        key: 'Escape'
      } as any;
      c.id = null;
      component.onValueChange('Another company', c);
      component.onValueChange('Another company', c, ev);
      expect(spy).toHaveBeenCalled();
    });

    it('should emit save on keydown.enter', () => {
      const spy = spyOn(component.save, 'emit');
      const ev = {
        key: 'Enter'
      } as any;
      component.onValueChange('Another company', c);
      component.onValueChange('Another company', c, ev);
      expect(spy).toHaveBeenCalled();
    });
  });

});
