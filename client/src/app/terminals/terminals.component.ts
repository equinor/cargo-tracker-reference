import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Country, Terminal } from '../shared/models/location';
import * as fromTerminals from './store/terminals.selectors';
import * as fromStatic from '../store/selectors/static.selectors';
import { filterTerminals, saveTerminal } from './store/terminals.actions';

@Component({
  selector: 'ctref-terminals',
  templateUrl: './terminals.component.html',
  styleUrls: [ './terminals.component.scss' ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TerminalsComponent implements OnInit {
  public terminals$: Observable<Terminal[]>;
  public sourceSystems$: Observable<string[]>;
  public countries$: Observable<Country[]>;
  public loading$: Observable<boolean>;
  public filters$: Observable<{ countryId: string }>;

  constructor(private store: Store<any>) {
  }

  ngOnInit() {
    this.countries$ = this.store.pipe(select(fromStatic.selectCountries));
    this.terminals$ = this.store.pipe(select(fromTerminals.selectTerminals));
    this.sourceSystems$ = this.store.pipe(select(fromTerminals.selectSourceSystems));
    this.filters$ = this.store.pipe(select(fromTerminals.selectTerminalFilters));
    this.loading$ = this.store.pipe(select(fromTerminals.selectLoading));
  }

  save(terminal: Terminal) {
    this.store.dispatch(saveTerminal({ terminal }));
  }

  onFilterChange(filters: { countryId: string }) {
    this.store.dispatch(filterTerminals({ filters }));
  }
}
