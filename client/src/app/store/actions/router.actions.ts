import { createAction, props } from '@ngrx/store';
import { NavigationExtras } from '@angular/router';

export const navigate = createAction(
  '[Router] Navigate',
  props<{commands: string[]; extras?: NavigationExtras}>()
);
