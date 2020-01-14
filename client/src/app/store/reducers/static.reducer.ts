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
  on(StaticActions.loadGradesSuccess, (state, action) => ( { ...state, grades: action.grades } )),
  on(StaticActions.loadCountriesSuccess, (state, action) => ( { ...state, countries: action.countries } )),
  on(StaticActions.loadRegionsSuccess, (state, action) => ( { ...state, regions: action.regions } )),
);

export function reducer(state: State | undefined, action: Action) {
  return staticReducer(state, action);
}
