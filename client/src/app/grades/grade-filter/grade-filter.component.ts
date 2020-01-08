import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FilterForm, FilterList } from '../../shared/filter-form';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { GradeFilter } from '../models';
import { Country } from '../../shared/models/location';

@Component({
  selector: 'ctref-grade-filter',
  templateUrl: './grade-filter.component.html',
  styleUrls: [ './grade-filter.component.scss' ]
})
export class GradeFilterComponent extends FilterForm<GradeFilter> {
  @Input()
  countries: Country[];
  @Output()
  add = new EventEmitter<void>();

  formConfig = {
    countryId: [],
    verified: [true]
  };
  serializer = map<GradeFilter, FilterList[]>(value => {
    const filters = [];
    console.log(value);
    if ( value.countryId ) {
      const c = ( this.countries || [] ).find(c => c.id === value.countryId);
      if ( c ) {
        filters.push({ key: 'countryId', value: c.name });
      }
    }
    switch ( value.verified ) {
      case true:
        filters.push({ key: 'verified', value: 'Only verified' });
        break;
      case false:
        filters.push({ key: 'verified', value: 'Only unverified' });
        break;
    }
    return filters;
  });

  constructor(fb: FormBuilder) {
    super(fb);
  }

  // serializer: OperatorFunction<{ countryId: string; verified: boolean }, string>;

}
