import { AfterViewInit, ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { combineLatest, Observable, ReplaySubject } from 'rxjs';
import { Grade } from '../shared/models/grade';
import { selectCountries, selectGrades } from '../store/selectors/static.selectors';
import { Column } from '@ngx-stoui/datatable';
import { Country } from '../shared/models/location';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'ctref-grades',
  templateUrl: './grades.component.html',
  styleUrls: [ './grades.component.scss' ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GradesComponent implements OnInit, AfterViewInit {
  public grades$: Observable<Grade[]>;
  private row$ = new ReplaySubject<Grade>();
  public rows$: Observable<Grade[]>;
  public countries$: Observable<Country[]>;
  public columns: Column[];

  constructor(private store: Store<any>) {
  }

  ngOnInit() {
    this.grades$ = this.store.pipe(select(selectGrades));
    this.rows$ = combineLatest(
      this.grades$,
      this.row$.pipe(startWith(null))
    ).pipe(
      map(([ rows, adding ]) => adding ? [ adding, ...rows ] : rows)
    );
    this.countries$ = this.store.pipe(select(selectCountries));
  }

  ngAfterViewInit() {
    requestAnimationFrame(() => {
      this.columns = [
        { prop: 'name', name: 'Name', flexGrow: 0, flexBasis: 120 },
        { prop: 'api', name: 'API', flexGrow: 0, flexBasis: 120 },
        { prop: 'sulphur', name: 'Sulphur', flexGrow: 0, flexBasis: 120 },
        { prop: 'source', name: 'Source system', flexGrow: 0, flexBasis: 120 },
        { prop: 'countryId', name: 'Country', flexGrow: 0, flexBasis: 120 },
        { prop: 'ocdName', name: 'OCD Name', flexGrow: 0, flexBasis: 120 },
        { prop: 'verified', name: 'Verified', flexGrow: 0, flexBasis: 120 },
        { prop: '', name: '' }
      ];
    });
  }

  add() {
    const g = {} as Grade;
    g.verified = false;
    g.id = null;
    g.historicAnalyses = [];
    this.row$.next(g);
  }
}
