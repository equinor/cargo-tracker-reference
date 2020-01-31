import { createAction, props } from '@ngrx/store';
import { Terminal } from '../../shared/models/location';

export const filterTerminals = createAction(
  '[Terminal] Filter',
  props<{ filters: { countryId: string } }>()
);

export const saveTerminal = createAction(
  '[Terminal] Save',
  props<{ terminal: Terminal }>()
);

export const saveTerminalSuccess = createAction(
  '[Terminal] Save success',
);

export const loading = createAction(
  '[Terminal] Loading',
  props<{ loading: boolean }>()
);


