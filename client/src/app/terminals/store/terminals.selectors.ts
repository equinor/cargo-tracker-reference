import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromTerminals from './terminals.reducer';
import * as fromStatic from '../../store/selectors/static.selectors';
import { selectQueryParams } from '../../store/selectors/router.selectors';

export const selectTerminalsState = createFeatureSelector<fromTerminals.State>(
  fromTerminals.terminalsFeatureKey
);
export const selectTerminalFilters = createSelector(
  selectTerminalsState,
  selectQueryParams,
  (state, params) => {
    if ( state.filters ) {
      return state.filters;
    }
    return {
      countryId: params.countryId,
    };
  });
export const selectTerminals = createSelector(
  selectTerminalFilters,
  fromStatic.selectTerminals,
  (filters, terminals) => {
    let filtered = [ ...( terminals || [] ) ];
    if ( filters.countryId ) {
      filtered = filtered.filter(t => t.countryId === filters.countryId);
    }
    return filtered;
  }
);
export const selectSourceSystems = createSelector(
  selectTerminals,
  terminals => Array.from(new Set(terminals.map(t => t.source)))
);
export const selectLoading = createSelector(
  selectTerminalsState,
  state => state.loading
);
