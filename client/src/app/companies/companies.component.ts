import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Company } from '../shared/models/company';
import { selectCompanies, selectCompanyFilter } from './store/company.selectors';
import { CompanyFilter } from './company-filter';
import { filterCompany } from './store/company.actions';

@Component({
  selector: 'ctref-companies',
  templateUrl: './companies.component.html',
  styleUrls: [ './companies.component.scss' ]
})
export class CompaniesComponent implements OnInit {
  public companies$: Observable<Company[]>;
  public filters$: Observable<CompanyFilter>;

  constructor(private store: Store<any>) {
  }

  ngOnInit() {
    this.companies$ = this.store.pipe(select(selectCompanies));
    this.filters$ = this.store.pipe(select(selectCompanyFilter));
  }

  onFilterChanged(filters: CompanyFilter) {
    this.store.dispatch(filterCompany({ filters }));
  }
}
