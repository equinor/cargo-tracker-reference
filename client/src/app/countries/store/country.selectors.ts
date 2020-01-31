import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromCountry from './country.reducer';
import * as fromRouter from '../../store/selectors/router.selectors';
import * as fromStatic from '../../store/selectors/static.selectors';

export const selectCountryState = createFeatureSelector<fromCountry.State>(
  fromCountry.countryFeatureKey
);

export const selectFilters = createSelector(
  selectCountryState,
  fromRouter.selectQueryParams,
  (state, params) => {
    if ( state.filters ) {
      return state.filters;
    }
    const { region } = params;
    return {
      region,
    };
  }
);

export const selectCountries = createSelector(
  selectFilters,
  fromStatic.selectCountries,
  (filters, countries) => {
    let filtered = [...(countries || [])];
    if (filters.region) {
      filtered = filtered.filter(f => f.regionId === filters.region);
    }
    return filtered;
  }
);
