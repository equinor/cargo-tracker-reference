import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromGrade from './grade.reducer';
import * as fromStatic from '../../store/selectors/static.selectors';
import { Grade } from '../../shared/models/grade';
import { selectQueryParams } from '../../store/selectors/router.selectors';

export const selectGradeState = createFeatureSelector<fromGrade.State>(
  fromGrade.gradeFeatureKey
);
export const selectLoading = createSelector(selectGradeState, state => state.loading);
export const selectFilter = createSelector(
  selectGradeState,
  selectQueryParams,
  (state, params) => {
    if ( state.filter ) {
      return state.filter;
    }
    return {
      countryId: params.countryId,
      verified: ( ![ 'false', 'true' ].includes(params.verified) ) ? null : params.verified === 'true',
      cancelled: params.cancelled === 'true'
    };
  });
export const selectGradeList = createSelector(
  fromStatic.selectGrades,
  selectFilter,
  (grades: Grade[], filter) => {
    let filteredList = [ ...grades ];
    if ( !filter.cancelled ) {
      filteredList = filteredList.filter(g => !g.cancelled);
    }
    if ( filter.verified ) {
      filteredList = filteredList.filter(g => g.verified);
    } else if ( filter.verified === false ) {
      filteredList = filteredList.filter(g => !g.verified);
    }
    if ( filter.countryId ) {
      filteredList = filteredList.filter(g => g.countryId === filter.countryId);
    }
    return filteredList;
  }
);
