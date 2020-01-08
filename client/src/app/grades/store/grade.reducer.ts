import { Action, createReducer, on } from '@ngrx/store';
import * as GradeActions from './grade.actions';

export const gradeFeatureKey = 'grade';

export interface State {
  loading: boolean;
}

export const initialState: State = {
  loading: false,
};

const gradeReducer = createReducer(
  initialState,
  on(GradeActions.loading, (state, action) => ({...state, loading: action.loading})),
);

export function reducer(state: State | undefined, action: Action) {
  return gradeReducer(state, action);
}
