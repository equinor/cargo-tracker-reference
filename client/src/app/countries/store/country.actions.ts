import { createAction, props } from '@ngrx/store';
import { CountryFilter } from '../country-filter';
import { Country } from '../../shared/models/location';

export const filterCountries = createAction(
  '[Country] Filter',
  props<{ filter: CountryFilter }>()
);

export const loading = createAction(
  '[Country] Loading',
  props<{loading: boolean}>()
);

export const saveCountry = createAction(
  '[Country] Save',
  props<{ country: Country }>()
);

export const saveCountrySuccess = createAction(
  '[Country] Save success',
);
