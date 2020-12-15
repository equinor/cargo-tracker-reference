import { createAction, props } from '@ngrx/store';
import { Grade } from '../../shared/models/grade';
import { Country, Terminal } from '../../shared/models/location';
import { Region } from '../../shared/models/region';
import { Company } from '../../shared/models/company';
import { TradingDesk } from '../../shared/models/trading-desk';

export const loadGrades = createAction(
  '[Static] Load grades'
);
export const loadGradesSuccess = createAction(
  '[Static] Load grades success',
  props<{ grades: Grade[] }>()
);

export const loadCountries = createAction(
  '[Static] Load countries'
);

export const loadCountriesSuccess = createAction(
  '[Static] Load countries success',
  props<{ countries: Country[] }>()
);

export const loadRegions = createAction(
  '[Static] Load regions'
);

export const loadRegionsSuccess = createAction(
  '[Static] Load regions success',
  props<{ regions: Region[] }>()
);

export const loadTerminals = createAction(
  '[Static] Load terminals'
);

export const loadTerminalsSuccess = createAction(
  '[Static] Load terminals success',
  props<{ terminals: Terminal[] }>()
);

export const loadCompanies = createAction(
  '[Static] Load companies'
);

export const loadCompaniesSuccess = createAction(
  '[Static] Load companies success',
  props<{ companies: Company[] }>()
);

export const loadEnvironmentSuccess = createAction(
  '[Static] Load environment info success',
  props<{ environment: any }>()
);

export const loadEnvironment = createAction(
  '[Static] Load environment info'
);

export const SetTradingDesk = createAction(
  '[Static] Set trading desk',
  props<{ tradingDesk: TradingDesk }>()
);
