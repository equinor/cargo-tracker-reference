import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

import * as StaticActions from '../actions/static.actions';
import * as ViewActions from '../actions/view.actions';
import { StaticService } from '../../static.service';
import { errorHandler } from './error-operator';


@Injectable()
export class StaticEffects {

  loadGrades$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadGrades),
    switchMap(() => this.service.grades()
      .pipe(
        map(grades => StaticActions.loadGradesSuccess({ grades })),
        errorHandler
      )
    )
  ));

  loadCountries$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCountries),
    switchMap(() => this.service.countries()
      .pipe(
        map(countries => StaticActions.loadCountriesSuccess({ countries })),
        errorHandler
      )
    )
  ));

  loadRegions$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadRegions),
    switchMap(() => this.service.regions()
      .pipe(
        map(regions => StaticActions.loadRegionsSuccess({ regions })),
        errorHandler
      )
    )
  ));

  loadTerminals$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadTerminals),
    switchMap(() => this.service.terminals()
      .pipe(
        map(terminals => StaticActions.loadTerminalsSuccess({ terminals })),
        errorHandler
      ))
  ));

  loadCompanies$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCompanies),
    switchMap(() => this.service.companies()
      .pipe(
        map(companies => StaticActions.loadCompaniesSuccess({ companies })),
        errorHandler
      ))
  ));

  constructor(private actions$: Actions, private service: StaticService) {
  }

}
