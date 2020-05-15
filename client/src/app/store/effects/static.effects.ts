import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, switchMap } from 'rxjs/operators';

import * as StaticActions from '../actions/static.actions';
import { StaticService } from '../../static.service';
import { errorHandler } from './error-operator';
import { storeInDb, checkDb } from 'src/ngforage/storage-operator';
import { NgForageCache } from 'ngforage';
import { ngfRootOptions } from 'src/ngforage/ngforage';


@Injectable()
export class StaticEffects {

  loadEnvironment$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadEnvironment),
    switchMap(() => this.service.environment()
      .pipe(
        map(environment => StaticActions.loadEnvironmentSuccess({ environment })),
        errorHandler
      )
    )
  ));

  loadGrades$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadGrades),
    switchMap(() => checkDb('grades', this.cache, this.service.grades().pipe(storeInDb('grades', this.cache)))      
      .pipe(                
        map(grades => StaticActions.loadGradesSuccess({ grades })),
        errorHandler
      )
    )
  ));

  loadCountries$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCountries),
    switchMap(() => checkDb('countries', this.cache, this.service.countries().pipe(storeInDb('countries', this.cache)))      
      .pipe(
        map(countries => StaticActions.loadCountriesSuccess({ countries })),
        errorHandler
      )
    )
  ));

  loadRegions$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadRegions),
    switchMap(() => checkDb('regions', this.cache, this.service.regions().pipe(storeInDb('regions', this.cache)))      
      .pipe(
        map(regions => StaticActions.loadRegionsSuccess({ regions })),
        errorHandler
      )
    )
  ));

  loadTerminals$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadTerminals),
    switchMap(() => checkDb('terminals', this.cache, this.service.terminals().pipe(storeInDb('terminals', this.cache)))      
      .pipe(
        map(terminals => StaticActions.loadTerminalsSuccess({ terminals })),
        errorHandler
      ))
  ));

  loadCompanies$ = createEffect(() => this.actions$.pipe(
    ofType(StaticActions.loadCompanies),
    switchMap(() => checkDb('companies', this.cache, this.service.companies().pipe(storeInDb('companies', this.cache)))      
      .pipe(
        map(companies => StaticActions.loadCompaniesSuccess({ companies })),
        errorHandler
      ))
  ));

  constructor(
    private actions$: Actions,
    private service: StaticService,    
    private cache: NgForageCache) {      
      cache.configure(ngfRootOptions);
  }

}
