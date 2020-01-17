import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { map } from 'rxjs/operators';

import * as CompanyActions from './company.actions';
import { navigate } from '../../store/actions/router.actions';


@Injectable()
export class CompanyEffects {

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.filterCompany),
    map(({ filters }) => navigate({ commands: [], extras: { queryParams: filters } }))
  ));

  constructor(private actions$: Actions) {
  }

}
