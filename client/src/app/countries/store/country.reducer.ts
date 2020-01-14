import { Action, createReducer, on } from '@ngrx/store';
import * as CountryActions from './country.actions';
import { CountryFilter } from '../country-filter';

export const countryFeatureKey = 'country';

export interface State {
  filters: CountryFilter;
  loading: boolean;
}

export const initialState: State = {
  filters: null,
  loading: false
};

const countryReducer = createReducer(
  initialState,
  on(CountryActions.loading, (state, action) => ( { ...state, loading: action.loading } )),
  on(CountryActions.filterCountries, (state, action) => ( { ...state, filters: action.filter } ))
);

export function reducer(state: State | undefined, action: Action) {
  return countryReducer(state, action);
}
