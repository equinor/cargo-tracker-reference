import { Action, createReducer, on } from '@ngrx/store';
import * as TerminalsActions from './terminals.actions';

export const terminalsFeatureKey = 'terminals';

export interface State {
  loading: boolean;
  filters: { countryId: string }
}

export const initialState: State = {
  loading: false,
  filters: null,
};

const terminalsReducer = createReducer(
  initialState,
  on(TerminalsActions.loading, (state, { loading }) => ( { ...state, loading } )),
  on(TerminalsActions.filterTerminals, (state, { filters }) => ( { ...state, filters } ))
);

export function reducer(state: State | undefined, action: Action) {
  return terminalsReducer(state, action);
}
