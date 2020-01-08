import { MetaReducer } from '@ngrx/store';
import * as fromStatic from './static.reducer';
import * as fromView from './view.reducer';
import { environment } from '../../../environments/environment';
import { routerReducer, RouterReducerState, SerializedRouterStateSnapshot } from '@ngrx/router-store';


export interface State {
  [ fromStatic.staticFeatureKey ]: fromStatic.State;
  router: RouterReducerState<SerializedRouterStateSnapshot>;
  view: fromView.State;
}

export const reducers = {
  staticState: fromStatic.reducer,
  router: routerReducer,
  view: fromView.reducer
};


export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];
