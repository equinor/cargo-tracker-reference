import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { map, switchMap } from 'rxjs/operators';

import * as TerminalsActions from './terminals.actions';
import { loading, saveTerminalSuccess } from './terminals.actions';
import { TerminalsService } from '../terminals.service';
import { loadTerminals, loadTerminalsSuccess } from '../../store/actions/static.actions';
import { navigate } from 'src/app/store/actions/router.actions';


@Injectable()
export class TerminalsEffects {

  save$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminal),
    switchMap(({ terminal }) => this.service.save(terminal).pipe(
      map(() => saveTerminalSuccess())
    ))
  ));

  saved$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminalSuccess),
    map(() => loadTerminals())
  ));

  loading$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminal, loadTerminals),
    map(() => loading({ loading: true }))
  ));

  loadingDone$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.saveTerminalSuccess, loadTerminalsSuccess),
    map(() => loading({ loading: false }))
  ));

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(TerminalsActions.filterTerminals),
    map(({ filters }) => navigate({ extras: { queryParams: filters }, commands: [] }))
  ));

  constructor(private actions$: Actions, private service: TerminalsService) {
  }

}
