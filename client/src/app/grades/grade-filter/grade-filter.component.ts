import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { GradeFilter } from '../models';
import { Country } from '../../shared/models/location';
import { FilterForm, FilterList } from '@ngx-stoui/common';

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
  @Output()
  uploadSheet = new EventEmitter<File>();

  formConfig = {
    countryId: [],
    verified: [true],
    cancelled: [false]
  };
  serializer = map<GradeFilter, FilterList[]>(value => {
    const filters = [];
    if ( value.countryId ) {
      const c = ( this.countries || [] ).find(c => c.id === value.countryId);
      if ( c ) {
        filters.push({ key: 'countryId', value: `Country: ${c.name}` });
      }
    }
    if (value.cancelled) {
      filters.push({key: 'cancelled', value: 'Show cancelled grades'});
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

  upload(event: Event) {
    const file: File = (event.currentTarget as HTMLInputElement).files[ 0 ];
    (event.currentTarget as HTMLInputElement).value = null;
    this.uploadSheet.emit(file);
  }
}
