import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountriesComponent } from './countries.component';
import { MemoizedSelector, Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import * as fromStatic from '../store/selectors/static.selectors';
import * as staticState from '../store/reducers/static.reducer';
import * as countriesState from './store/country.reducer';
import * as fromCountries from './store/country.selectors';
import { take } from 'rxjs/operators';
import Spy = jasmine.Spy;

const regions = [
  { name: 'Europe', id: '1' },
  { name: 'Asia', id: '2' },
];

const countries = [
  { name: 'Norway', id: '1' },
  { name: 'Sweden', id: '2' },
  { name: 'Denmark', id: '3' },
];

describe('CountriesComponent', () => {
  let component: CountriesComponent;
  let fixture: ComponentFixture<CountriesComponent>;
  let store: MockStore<any>;
  let spy: Spy;
  let mockSelectRegions: MemoizedSelector<staticState.State, any>;
  let mockSelectCountries: MemoizedSelector<countriesState.State, any>;
  let mockSelectFilters: MemoizedSelector<countriesState.State, any>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [ provideMockStore() ],
      declarations: [ CountriesComponent ],
      schemas: [ NO_ERRORS_SCHEMA ]
    });

    await TestBed.compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CountriesComponent);
    component = fixture.componentInstance;
    store = TestBed.get(Store);
    mockSelectRegions = store.overrideSelector(fromStatic.selectRegions, regions as any);
    mockSelectCountries = store.overrideSelector(fromCountries.selectCountries, countries as any);
    mockSelectFilters = store.overrideSelector(fromCountries.selectFilters, {});

    spy = spyOn(store, 'dispatch').and.callThrough();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dispatch filterCountries', () => {
    component.onFilterChange({ region: '1' });
    expect(spy).toHaveBeenCalled();
  });

  it('should dispatch saveCountry', () => {
    component.save(countries[ 0 ] as any);
    expect(spy).toHaveBeenCalled();
  });

  it('should have a list of regions', (done: DoneFn) => {
    component.regions$.pipe(take(1)).toPromise()
      .then(r => {
        expect(r.length).toEqual(regions.length);
        done();
      });
  });

  it('should have a list of countries', (done: DoneFn) => {
    component.countries$.pipe(take(1)).toPromise()
      .then(c => {
        expect(c.length).toEqual(countries.length);
        done();
      });
  });
});
