import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromStatic from '../reducers/static.reducer';

export const selectStaticState = createFeatureSelector<fromStatic.State>(
  fromStatic.staticFeatureKey
);
