import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { BehaviorSubject, combineLatest, Observable } from 'rxjs';
import { Company } from '../shared/models/company';
import { selectCompanies, selectCompanyFilter, selectCompanyLoading } from './store/company.selectors';
import { CompanyFilter } from './company-filter';
import { cancelCompany, filterCompany, saveCompany, verifyCompany } from './store/company.actions';
import { MatDialog } from '@angular/material';
import { map, take } from 'rxjs/operators';

@Component({
  selector: 'ctref-companies',
  templateUrl: './companies.component.html',
  styleUrls: [ './companies.component.scss' ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CompaniesComponent implements OnInit {
  public rows$: Observable<Company[]>;
  public loading$: Observable<boolean>;
  readonly row$ = new BehaviorSubject<Company>(null);
  public filters$: Observable<CompanyFilter>;
  private companies$: Observable<Company[]>;

  constructor(private store: Store<any>, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.companies$ = this.store.pipe(select(selectCompanies));
    this.rows$ = combineLatest(
      this.companies$,
      this.row$
    ).pipe(
      map(([ rows, adding ]) => adding ? [ adding, ...rows ] : rows)
    );
    this.filters$ = this.store.pipe(select(selectCompanyFilter));
    this.loading$ = this.store.pipe(select(selectCompanyLoading));
  }

  onFilterChanged(filters: CompanyFilter) {
    this.store.dispatch(filterCompany({ filters }));
  }

  save(company: Company) {
    this.row$.next(null);
    this.store.dispatch(saveCompany({ company }));
  }

  verify(company: Company) {
    this.store.dispatch(verifyCompany({ company }));
  }

  add() {
    const c = {} as Company;
    c.id = null;
    c.aliases = [];
    c.verified = false;
    c.name = '';
    this.row$.next(c);
  }

  cancel(company: Company) {
    if ( !company.id ) {
      this.row$.next(null);
    } else {
      this.store.dispatch(cancelCompany({ company }));
    }
  }
}
