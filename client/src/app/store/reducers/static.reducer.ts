import { Action, createReducer, on } from '@ngrx/store';
import * as StaticActions from '../actions/static.actions';
import { Region } from '../../shared/models/region';
import { Country, Terminal } from '../../shared/models/location';
import { Grade } from '../../shared/models/grade';
import { Company } from '../../shared/models/company';

export const staticFeatureKey = 'staticState';

export interface State {
  regions: Region[];
  countries: Country[];
  grades: Grade[];
  terminals: Terminal[];
  companies: Company[];
}

export const initialState: State = {
  regions: null,
  countries: null,
  grades: [],
  terminals: null,
  companies: null
};

const staticReducer = createReducer(
  initialState,
  on(StaticActions.loadGradesSuccess, (state, { grades }) => ( { ...state, grades } )),
  on(StaticActions.loadCountriesSuccess, (state, { countries }) => ( { ...state, countries } )),
  on(StaticActions.loadRegionsSuccess, (state, { regions }) => ( { ...state, regions } )),
  on(StaticActions.loadTerminalsSuccess, (state, { terminals }) => ( { ...state, terminals } )),
  on(StaticActions.loadCompaniesSuccess, (state, { companies }) => ( { ...state, companies } )),
);

export function reducer(state: State | undefined, action: Action) {
  return staticReducer(state, action);
}
