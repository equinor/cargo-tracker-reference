import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { concatMap, map, switchMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

import * as GradeActions from './grade.actions';
import { GradeService } from '../grade.service';
import { loadGrades } from '../../store/actions/static.actions';


@Injectable()
export class GradeEffects {


  save$ = createEffect(() => this.actions$.pipe(
    ofType(GradeActions.saveGrade),
    switchMap(action => this.service.save(action.grade)
      .pipe(
        map(res => GradeActions.saveGradeSuccess())
      ))
    )
  );

  cancel$ = createEffect(() => this.actions$.pipe(
    ofType(GradeActions.cancelGrade),
    switchMap(action => this.service.cancel(action.grade)
      .pipe(
        map(res => GradeActions.saveGradeSuccess())
      ))
  ));

  loading$ = createEffect(() => this.actions$.pipe(
    ofType(GradeActions.cancelGrade, GradeActions.saveGrade),
    map(() => GradeActions.loading({ loading: true }))
  ));

  loadingDone$ = createEffect(() => this.actions$.pipe(
    ofType(GradeActions.saveGradeSuccess),
    map(() => GradeActions.loading({ loading: false }))
  ));

  loadGrades$ = createEffect(() => this.actions$.pipe(
    ofType(GradeActions.saveGradeSuccess),
    map(() => loadGrades())
  ));


  constructor(private actions$: Actions, private service: GradeService) {
  }

}
