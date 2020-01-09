import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { combineLatest, Observable, ReplaySubject } from 'rxjs';
import { Grade } from '../shared/models/grade';
import * as fromStatic from '../store/selectors/static.selectors';
import * as fromGrade from './store/grade.selectors';
import { Country } from '../shared/models/location';
import { map, startWith } from 'rxjs/operators';
import { cancelGrade, filterGrade, saveGrade, verifyGrade } from './store/grade.actions';
import { GradeFilter } from './models';
import { format, startOfMonth } from 'date-fns';


@Component({
  selector: 'ctref-grades',
  templateUrl: './grades.component.html',
  styleUrls: [ './grades.component.scss' ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GradesComponent implements OnInit {
  public grades$: Observable<Grade[]>;
  private row$ = new ReplaySubject<Grade>();
  public rows$: Observable<Grade[]>;
  public countries$: Observable<Country[]>;
  public loading$: Observable<boolean>;
  public filters$: Observable<GradeFilter>;

  constructor(private store: Store<any>) {
  }

  ngOnInit() {
    this.grades$ = this.store.pipe(select(fromGrade.selectGradeList));
    this.filters$ = this.store.pipe(select(fromGrade.selectFilter));
    this.loading$ = this.store.pipe(select(fromGrade.selectLoading));
    this.rows$ = combineLatest(
      this.grades$,
      this.row$.pipe(startWith(null))
    ).pipe(
      map(([ rows, adding ]) => adding ? [ adding, ...rows ] : rows)
    );
    this.countries$ = this.store.pipe(select(fromStatic.selectCountries));
  }

  add() {
    const g = {} as Grade;
    g.verified = false;
    g.source = 'Cargo Tracking';
    g.validFrom = format(startOfMonth(new Date()), 'YYYY-MM-DD')
    g.id = null;
    g.historicAnalyses = [];
    this.row$.next(g);
  }

  save(grade: Grade) {
    this.store.dispatch(saveGrade({ grade }));
    this.row$.next(null);
  }

  cancel() {
    this.row$.next(null);
  }

  verify(grade: Grade) {
    this.store.dispatch(verifyGrade({ grade }));
  }

  onFilterChange(filter: GradeFilter) {
    this.store.dispatch(filterGrade({ filter }));
  }

  cancelGrade(grade: Grade) {
    if (grade.id) {
      this.store.dispatch(cancelGrade({ grade }));
    } else {
      this.row$.next(null);
    }
  }
}
