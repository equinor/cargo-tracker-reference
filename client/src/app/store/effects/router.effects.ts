import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { Router } from '@angular/router';
import { navigate } from '../actions/router.actions';
import { map } from 'rxjs/operators';

@Injectable()
export class RouterEffects {

  @Effect({dispatch: false})
  navigate$ = this.actions$.pipe(
    ofType(navigate),
    map(action => ( { commands: action.commands, extras: action.extras || { queryParamsHandling: 'preserve' } } )),
    map(pl => this.router.navigate(pl.commands, pl.extras))
  );

  constructor(private actions$: Actions, private router: Router) {
  }
}
