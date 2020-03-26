import { createAction, props } from '@ngrx/store';
import { HttpErrorResponse } from '@angular/common/http';

export const loadViews = createAction(
  '[View] Load Views',
  props<{modules: any[]}>()
);

export const error = createAction(
  '[View] Global error handler',
  props<{error: HttpErrorResponse}>()
);


