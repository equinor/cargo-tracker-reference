import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromGrade from './grade.reducer';

export const selectGradeState = createFeatureSelector<fromGrade.State>(
  fromGrade.gradeFeatureKey
);
