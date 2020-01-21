import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Country } from '../../shared/models/location';
import { map } from 'rxjs/operators';
import { FormBuilder } from '@angular/forms';
import { FilterForm, FilterList } from '@ngx-stoui/common';

@Component({
  selector: 'ctref-terminal-filter',
  templateUrl: './terminal-filter.component.html',
  styleUrls: [ './terminal-filter.component.scss' ]
})
export class TerminalFilterComponent extends FilterForm<any> {
  @Input()
  countries: Country[];
  @Output()
  add = new EventEmitter<void>();
  @Output()
  uploadSheet = new EventEmitter<File>();

  formConfig = {
    countryId: [],
  };
  serializer = map<{ countryId: string }, FilterList[]>(value => {
    const filters = [];
    if ( value.countryId ) {
      const c = this.countries.find(cc => cc.id === value.countryId);
      if ( c ) {
        filters.push({
          key: 'countryId',
          value: c.name
        });
      }
    }
    return filters;
  });

  constructor(fb: FormBuilder) {
    super(fb);
  }

}
