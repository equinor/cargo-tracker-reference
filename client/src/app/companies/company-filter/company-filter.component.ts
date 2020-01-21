import { Component, EventEmitter, Output } from '@angular/core';
import { map } from 'rxjs/operators';
import { FormBuilder } from '@angular/forms';
import { CompanyFilter } from '../company-filter';
import { FilterForm, FilterList } from '@ngx-stoui/common';

@Component({
  selector: 'ctref-company-filter',
  templateUrl: './company-filter.component.html',
  styleUrls: [ './company-filter.component.scss' ]
})
export class CompanyFilterComponent extends FilterForm<CompanyFilter> {
  @Output()
  add = new EventEmitter<void>();
  @Output()
  uploadSheet = new EventEmitter<File>();

  formConfig = {
    countryId: [],
    verified: [ true ],
    cancelled: [ false ]
  };
  serializer = map<CompanyFilter, FilterList[]>(value => {
    const filters = [];
    if ( value.cancelled ) {
      filters.push({ key: 'cancelled', value: 'Show cancelled companies' });
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

}
