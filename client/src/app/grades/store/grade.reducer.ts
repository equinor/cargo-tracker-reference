import { Action, createReducer, on } from '@ngrx/store';
import * as GradeActions from './grade.actions';
import { GradeFilter } from '../models';

export const gradeFeatureKey = 'grade';

export interface State {
  loading: boolean;
  filter: GradeFilter;
}

export const initialState: State = {
  loading: false,
  filter: null
};

const gradeReducer = createReducer(
  initialState,
  on(GradeActions.loading, (state, action) => ( { ...state, loading: action.loading } )),
  on(GradeActions.filterGrade, (state, action) => ( { ...state, filter: action.filter } ))
);

export function reducer(state: State | undefined, action: Action) {
  return gradeReducer(state, action);
}
