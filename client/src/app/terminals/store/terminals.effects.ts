import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { map, switchMap, tap, withLatestFrom } from 'rxjs/operators';

import * as TerminalsActions from './terminals.actions';
import { loading, saveTerminalSuccess } from './terminals.actions';
import { TerminalsService } from '../terminals.service';
import { loadTerminals, loadTerminalsSuccess } from '../../store/actions/static.actions';
import { navigate } from 'src/app/store/actions/router.actions';
import { errorHandler } from 'src/app/store/effects/error-operator';
import { error } from 'src/app/store/actions/view.actions';
import { deleteCache } from 'src/ngforage/storage-operator';
import { NgForageCache } from 'ngforage';
import { ngfRootOptions } from 'src/ngforage/ngforage';
import { select, Store } from '@ngrx/store';
import * as fromStatic from '../../store/selectors/static.selectors';


@Injectable()
export class TerminalsEffects {

  save$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminal),
    switchMap(({ terminal }) => this.service.save(terminal).pipe(
      map(() => saveTerminalSuccess()),
      errorHandler
    ))
  ));

  saved$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminalSuccess),
    withLatestFrom(this.store.pipe(select(fromStatic.selectTradingDesk))),
    switchMap(([a, tradingDesk]) =>
      tap(deleteCache(`${tradingDesk}-terminals`, this.cache))),
    map(() => loadTerminals())
  ));

  loading$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminal, loadTerminals),
    map(() => loading({ loading: true }))
  ));

  loadingDone$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminalSuccess, loadTerminalsSuccess, error),
    map(() => loading({ loading: false }))
  ));

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.filterTerminals),
    map(({ filters }) => navigate({ extras: { queryParams: filters }, commands: [] }))
  ));

  constructor(
    private actions$: Actions,
    private service: TerminalsService,
    private store: Store<any>,
    private cache: NgForageCache) {
      cache.configure(ngfRootOptions);
  }

}
