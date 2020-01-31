import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryListComponent } from './country-list.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { LatLngPipe } from '../../shared/lat-lng.pipe';
import { MatSelectChange } from '@angular/material';
import { Country } from '../../shared/models/location';

describe('CountryListComponent', () => {
  let component: CountryListComponent;
  let fixture: ComponentFixture<CountryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ CountryListComponent, LatLngPipe ],
        schemas: [ NO_ERRORS_SCHEMA ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CountryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update the selected country with the new region', () => {
    const regionId = '2';
    const ev = new MatSelectChange(null, regionId);
    const c = {} as Country;
    c.name = 'A Country';
    c.id = '1';
    const spy = spyOn(component.save, 'emit');
    component.onSelectRegion(ev, c);
    expect(spy).toHaveBeenCalled();
  });
});
