import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, switchMap, tap } from 'rxjs/operators';

import * as CountryActions from './country.actions';
import { CountryService } from '../country.service';
import { navigate } from '../../store/actions/router.actions';
import { loadCountries } from '../../store/actions/static.actions';
import { errorHandler } from 'src/app/store/effects/error-operator';
import { NgForageCache } from 'ngforage';
import { ngfRootOptions } from 'src/ngforage/ngforage';
import { deleteCache } from 'src/ngforage/storage-operator';



@Injectable()
export class CountryEffects {

  save$ = createEffect(() => this.actions$.pipe(
    ofType(CountryActions.saveCountry),
    switchMap(action => this.service.save(action.country)
      .pipe(
        map(() => CountryActions.saveCountrySuccess()),
        errorHandler
      )
    )
  ));

  saveSuccess$ = createEffect(() => this.actions$.pipe(
    ofType(CountryActions.saveCountrySuccess),
    tap(deleteCache('countries', this.cache)),
    map(() => loadCountries())
  ));

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(CountryActions.filterCountries),
    map(action => navigate({ commands: [], extras: { queryParams: action.filter } }))
  ));

  constructor(
    private actions$: Actions, 
    private service: CountryService,
    private cache: NgForageCache) {      
      cache.configure(ngfRootOptions);
  }

}
