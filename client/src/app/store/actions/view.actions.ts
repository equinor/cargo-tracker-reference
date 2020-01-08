import { createAction, props } from '@ngrx/store';

export const loadViews = createAction(
  '[View] Load Views',
  props<{modules: any[]}>()
);




