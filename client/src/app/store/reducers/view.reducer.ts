import { Action, createReducer, on } from '@ngrx/store';
import * as ViewActions from '../actions/view.actions';


export const viewFeatureKey = 'view';

export interface State {
  modules: any[];
}

export const initialState: State = {
  modules: []
};

const viewReducer = createReducer(
  initialState,

  on(ViewActions.loadViews, (state, action) => ({...state, modules: action.modules})),
);

export function reducer(state: State | undefined, action: Action) {
  return viewReducer(state, action);
}
