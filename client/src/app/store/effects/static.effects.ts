import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, concatMap, switchMap } from 'rxjs/operators';
import { EMPTY, of } from 'rxjs';

import * as StaticActions from '../actions/static.actions';
import { StaticService } from '../../static.service';


@Injectable()
export class StaticEffects {

  loadGrades$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadGrades),
    switchMap(() => this.service.grades()
      .pipe(
        map(grades => StaticActions.loadGradesSuccess({ grades }))
      )
    )
  ));

  loadCountries$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCountries),
    switchMap(() => this.service.countries()
      .pipe(
        map(countries => StaticActions.loadCountriesSuccess({ countries }))
      )
    )
  ));

  loadRegions$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadRegions),
    switchMap(() => this.service.regions()
      .pipe(
        map(regions => StaticActions.loadRegionsSuccess({ regions }))
      )
    )
  ));

  loadTerminals$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadTerminals),
    switchMap(() => this.service.terminals()
      .pipe(
        map(terminals => StaticActions.loadTerminalsSuccess({ terminals }))
      ))
  ));

  loadCompanies$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCompanies),
    switchMap(() => this.service.companies()
      .pipe(
        map(companies => StaticActions.loadCompaniesSuccess({ companies }))
      ))
  ));

  constructor(private actions$: Actions, private service: StaticService) {
  }

}
