import { createAction, props } from '@ngrx/store';
import { Company } from '../../shared/models/company';
import { CompanyFilter } from '../company-filter';

export const filterCompany = createAction(
  '[Company] Filter',
  props<{ filters: CompanyFilter }>()
);

export const saveCompany = createAction(
  '[Company] Save',
  props<{ company: Company }>()
);

export const verifyCompany = createAction(
  '[Company] Verify',
  props<{ company: Company }>()
);

export const cancelCompany = createAction(
  '[Company] Cancel',
  props<{ company: Company }>()
);

export const saveCompanySuccess = createAction(
  '[Company] Save success',
);

export const companyLoading = createAction(
  '[Company] Loading',
  props<{ loading: boolean }>()
);
