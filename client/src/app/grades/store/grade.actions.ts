import { createAction, props } from '@ngrx/store';
import { Grade } from '../../shared/models/grade';

export const saveGrade = createAction(
  '[Grade] Save',
  props<{ grade: Grade }>()
);

export const saveGradeSuccess = createAction(
  '[Grade] Save Success'
);

export const cancelGrade = createAction(
  '[Grade] Cancel',
  props<{ grade: Grade }>()
);

export const loading = createAction(
  '[Grade] Loading',
  props<{ loading: boolean }>()
);
