import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminalsComponent } from './terminals.component';
import { MemoizedSelector, Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { State } from './store/terminals.reducer';
import { State as StaticState } from '../store/reducers/static.reducer';
import * as fromStatic from '../store/selectors/static.selectors';
import * as fromTerminals from './store/terminals.selectors';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { filterTerminals, saveTerminal } from './store/terminals.actions';
import { Terminal } from '../shared/models/location';
import Spy = jasmine.Spy;

describe('TerminalsComponent', () => {
  let component: TerminalsComponent;
  let fixture: ComponentFixture<TerminalsComponent>;
  let store: MockStore<any>;
  let spy: Spy;
  let terminal: Terminal;
  let selectCountries: MemoizedSelector<StaticState, any>;
  let selectTerminals: MemoizedSelector<State, any>;
  let selectSourceSystems: MemoizedSelector<State, any>;
  let selectTerminalFilters: MemoizedSelector<State, any>;
  let selectLoading: MemoizedSelector<State, any>;
  let selectTradingDesk: MemoizedSelector<State, any>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [ TerminalsComponent ],
      providers: [ provideMockStore() ],
      schemas: [ NO_ERRORS_SCHEMA ]
    });

    await TestBed.compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalsComponent);
    component = fixture.componentInstance;
    store = TestBed.get(Store);
    selectCountries = store.overrideSelector(fromStatic.selectCountries, []);
    selectTerminals = store.overrideSelector(fromTerminals.selectTerminals, []);
    selectSourceSystems = store.overrideSelector(fromTerminals.selectSourceSystems, []);
    selectTerminalFilters = store.overrideSelector(fromTerminals.selectTerminalFilters, { countryId: '' });
    selectLoading = store.overrideSelector(fromTerminals.selectLoading, false);
    selectTradingDesk = store.overrideSelector(fromStatic.selectTradingDesk, 'Crude');
    terminal = {} as Terminal;
    terminal.id = '1';
    terminal.name = 'Name';

    spy = spyOn(store, 'dispatch').and.callThrough();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should dispatch save', () => {
    const action = saveTerminal({ terminal });
    component.save(terminal);
    expect(spy).toHaveBeenCalledWith(action);
  });
  it('should dispatch filter', () => {
    const filters = { countryId: '3' };
    const action = filterTerminals({ filters });
    component.onFilterChange(filters);
    expect(spy).toHaveBeenCalledWith(action);
  });

});
