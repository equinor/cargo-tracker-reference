import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { concatMap, map } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

import * as ViewActions from '../actions/view.actions';
import { ErrorHandlerService } from '@ngx-stoui/error-handler';
import { AppInsightsService } from 'src/app/app-insights/app-insights.service';


@Injectable()
export class ViewEffects {


  loadViews$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ViewActions.loadViews),
      /** An EMPTY observable only emits completion. Replace with your own observable API request */
      concatMap(() => EMPTY)
    );
  });

  errorHandler$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ViewActions.error),
      map(act => this.errorHandler.handler(act.error))
    );
  }, { dispatch: false });

  logErrorToInsight$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ViewActions.error),
      map(act => act.error),
      map(error => this.appInsightsService.logTrace(error.message, error))
    );
  }, { dispatch: false });


  constructor(private actions$: Actions, private errorHandler: ErrorHandlerService, private appInsightsService: AppInsightsService) {}

}
