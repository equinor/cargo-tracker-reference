import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromStatic from '../reducers/static.reducer';

export const selectStaticState = createFeatureSelector<fromStatic.State>(
  fromStatic.staticFeatureKey
);

export const selectGrades = createSelector(selectStaticState, state => state.grades);
export const selectCountries = createSelector(selectStaticState, state => state.countries);
export const selectRegions = createSelector(selectStaticState, state => state.regions);
export const selectRegionsWithCountries = createSelector(
  selectRegions,
  selectCountries,
  (regions, countries) => {
    return [({name: 'Unmapped', id: null} as any), ...regions]
      .map(r => ( { ...r, countries: countries.filter(c => c.regionId === r.id) } ));
  }
);
