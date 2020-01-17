import { Action, createReducer, on } from '@ngrx/store';
import * as CompanyActions from './company.actions';
import { CompanyFilter } from '../company-filter';

export const companyFeatureKey = 'company';

export interface State {
  filters: CompanyFilter;
  loading: boolean;
}

export const initialState: State = {
  filters: null,
  loading: false
};

const companyReducer = createReducer(
  initialState,
  on(CompanyActions.companyLoading, (state, { loading }) => ( { ...state, loading } )),
  on(CompanyActions.filterCompany, (state, { filters }) => ( { ...state, filters } )),
);

export function reducer(state: State | undefined, action: Action) {
  return companyReducer(state, action);
}
