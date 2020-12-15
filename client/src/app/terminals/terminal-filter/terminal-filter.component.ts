import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Country } from '../../shared/models/location';
import { map } from 'rxjs/operators';
import { FormBuilder } from '@angular/forms';
import { FilterForm, FilterList } from '@ngx-stoui/common';
import { TradingDesk } from '../../shared/models/trading-desk';

@Component({
  selector: 'ctref-terminal-filter',
  templateUrl: './terminal-filter.component.html',
  styleUrls: [ './terminal-filter.component.scss' ]
})
export class TerminalFilterComponent extends FilterForm<any> implements OnInit{
  @Input()
  countries: Country[];
  @Input()
  tradingDesk: TradingDesk;
  @Output()
  add = new EventEmitter<void>();
  @Output()
  uploadSheet = new EventEmitter<File>();
  @Output()
  tradingDeskChanged = new EventEmitter<TradingDesk>();

  public tradingDesks = Object.keys(TradingDesk);
  formConfig: { [ p: string ]: any };


  serializer = map<{ countryId: string, tradingDesk: TradingDesk }, FilterList[]>(value => {
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

  ngOnInit(): void {
    this.formConfig = {
      countryId: [],
      tradingDesk: []
    };
    super.ngOnInit();
    this.form.setValue({tradingDesk: this.tradingDesk, countryId: null});
  }

  constructor(fb: FormBuilder) {
    super(fb);
  }

}
