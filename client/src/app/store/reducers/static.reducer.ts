import { Action, createReducer, on } from '@ngrx/store';
import * as StaticActions from '../actions/static.actions';
import { Region } from '../../shared/models/region';
import { Country, Terminal } from '../../shared/models/location';
import { Grade } from '../../shared/models/grade';

export const staticFeatureKey = 'staticState';

export interface State {
  regions: Region[];
  countries: Country[];
  grades: Grade[];
  terminals: Terminal[];
}

export const initialState: State = {
  regions: null,
  countries: null,
  grades: [],
  terminals: null,
};

const staticReducer = createReducer(
  initialState,
  on(StaticActions.loadGradesSuccess, (state, { grades }) => ( { ...state, grades } )),
  on(StaticActions.loadCountriesSuccess, (state, { countries }) => ( { ...state, countries } )),
  on(StaticActions.loadRegionsSuccess, (state, { regions }) => ( { ...state, regions } )),
  on(StaticActions.loadTerminalsSuccess, (state, { terminals }) => ( { ...state, terminals } )),
);

export function reducer(state: State | undefined, action: Action) {
  return staticReducer(state, action);
}
