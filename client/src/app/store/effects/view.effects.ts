import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { concatMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

import * as ViewActions from '../actions/view.actions';


@Injectable()
export class ViewEffects {


  loadViews$ = createEffect(() => {
    return this.actions$.pipe( 

      ofType(ViewActions.loadViews),
      /** An EMPTY observable only emits completion. Replace with your own observable API request */
      concatMap(() => EMPTY)
    );
  });


  constructor(private actions$: Actions) {}

}
