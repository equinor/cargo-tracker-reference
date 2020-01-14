import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { map, switchMap } from 'rxjs/operators';

import * as CountryActions from './country.actions';
import { CountryService } from '../country.service';
import { navigate } from '../../store/actions/router.actions';


@Injectable()
export class CountryEffects {

  save$ = createEffect(() => this.actions$.pipe(
    ofType(CountryActions.saveCountry),
    switchMap(action => this.service.save(action.country)
      .pipe(
        map(() => CountryActions.saveCountrySuccess())
      )
    )
  ));

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(CountryActions.filterCountries),
    map(action => navigate({ commands: [], extras: { queryParams: action.filter } }))
  ));

  constructor(private actions$: Actions, private service: CountryService) {
  }

}
