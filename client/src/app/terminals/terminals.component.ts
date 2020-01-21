import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Country, Terminal } from '../shared/models/location';
import { selectLoading, selectSourceSystems, selectTerminalFilters, selectTerminals } from './store/terminals.selectors';
import { selectCountries } from '../store/selectors/static.selectors';
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
    this.terminals$ = this.store.pipe(select(selectTerminals));
    this.sourceSystems$ = this.store.pipe(select(selectSourceSystems));
    this.countries$ = this.store.pipe(select(selectCountries));
    this.filters$ = this.store.pipe(select(selectTerminalFilters));
    this.loading$ = this.store.pipe(select(selectLoading));
  }

  save(terminal: Terminal) {
    this.store.dispatch(saveTerminal({ terminal }));
  }

  onFilterChange(filters: { countryId: string }) {
    this.store.dispatch(filterTerminals({ filters }));
  }
}
