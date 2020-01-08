import { Action, createReducer, on } from '@ngrx/store';
import * as StaticActions from '../actions/static.actions';

type TradingArea = any;
type Region = any;
type Country = any;
type Grade = any;
type Terminal = any;

export const staticFeatureKey = 'staticState';

export interface State {
  tradingAreas: TradingArea[];
  regions: Region[];
  countries: Country[];
  grades: Grade[];
  terminals: Terminal[];
}

export const initialState: State = {
  tradingAreas: null,
  regions: null,
  countries: null,
  grades: null,
  terminals: null,
};

const staticReducer = createReducer(
  initialState,
  // on(StaticActions.loadStatics, state => state),
);

export function reducer(state: State | undefined, action: Action) {
  return staticReducer(state, action);
}
