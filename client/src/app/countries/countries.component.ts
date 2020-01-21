import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Region } from '../shared/models/region';
import { Country } from '../shared/models/location';
import * as fromStatic from '../store/selectors/static.selectors';
import * as fromCountry from './store/country.selectors';
import { CountryFilter } from './country-filter';
import { filterCountries, saveCountry } from './store/country.actions';

@Component({
  selector: 'ctref-countries',
  templateUrl: './countries.component.html',
  styleUrls: [ './countries.component.scss' ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CountriesComponent implements OnInit {
  public regions$: Observable<Region[]>;
  public countries$: Observable<Country[]>;
  public filters$: Observable<CountryFilter>;

  constructor(private store: Store<any>) {
  }

  ngOnInit() {
    this.regions$ = this.store.pipe(select(fromStatic.selectRegions));
    this.countries$ = this.store.pipe(select(fromCountry.selectCountries));
    this.filters$ = this.store.pipe(select(fromCountry.selectFilters));
  }

  onFilterChange(filter: CountryFilter) {
    this.store.dispatch(filterCountries({ filter }));
  }

  save(country: Country) {
    this.store.dispatch(saveCountry({ country }));
  }
}
