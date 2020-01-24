import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromCompany from './company.reducer';
import * as fromRouter from '../../store/selectors/router.selectors';
import * as fromStatic from '../../store/selectors/static.selectors';

export const selectCompanyState = createFeatureSelector<fromCompany.State>(
  fromCompany.companyFeatureKey
);
export const selectCompanyLoading = createSelector(selectCompanyState, state => state.loading);
export const selectCompanyFilter = createSelector(
  selectCompanyState,
  fromRouter.selectQueryParams,
  (state, params) => {
    if ( state.filters ) {
      return state.filters;
    }
    const cancelled = params.cancelled === 'true';
    const verifiedTrue = params.verified === 'true';
    const verifiedFalse = params.verified === 'false';
    const verified = verifiedTrue ? verifiedTrue : verifiedFalse ? verifiedFalse : null;
    return {
      cancelled,
      verified
    };
  }
);
export const selectCompanies = createSelector(
  fromStatic.selectCompanies,
  selectCompanyFilter,
  (list, filters) => {
    const { cancelled, verified } = filters;
    let filtered = [ ...( list || [] ) ];
    if ( !cancelled ) {
      filtered = filtered.filter(f => !f.cancelled);
    }
    switch ( verified ) {
      case true:
        filtered = filtered.filter(f => f.verified);
        break;
      case false:
        filtered = filtered.filter(f => !f.verified);
        break;
    }
    return filtered;
  }
);
