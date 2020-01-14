import { Component, Input, OnInit } from '@angular/core';
import { FilterForm, FilterList } from '../../shared/filter-form';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { CountryFilter } from '../country-filter';
import { Region } from '../../shared/models/region';

@Component({
  selector: 'ctref-country-filter',
  templateUrl: './country-filter.component.html',
  styleUrls: [ './country-filter.component.scss' ]
})
export class CountryFilterComponent extends FilterForm<CountryFilter> {
  @Input()
  regions: Region[];

  formConfig = {
    region: [],
    cancelled: [],
  };
  serializer = map<CountryFilter, FilterList[]>(filters => {
    const list = [];
    if ( filters.region ) {
      const r = ( this.regions || [] ).find(region => region.id === filters.region);
      if ( r ) {
        list.push({ key: 'region', value: r.name });
      }
    }
    return list;
  });

  constructor(fb: FormBuilder) {
    super(fb);
  }


}
