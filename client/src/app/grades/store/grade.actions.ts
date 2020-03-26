import { createAction, props } from '@ngrx/store';
import { Grade } from '../../shared/models/grade';
import { GradeFilter } from '../models';

export const filterGrade = createAction(
  '[Grade] Filter',
  props<{ filter: GradeFilter }>()
);

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

export const verifyGrade = createAction(
  '[Grade] Verify',
  props<{ grade: Grade }>()
);

export const uploadGrades = createAction(
  '[Grade] Upload',
  props<{ file: File }>()
);
