import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { map, switchMap, tap } from 'rxjs/operators';

import * as CompanyActions from './company.actions';
import { navigate } from '../../store/actions/router.actions';
import { CompanyService } from '../company.service';
import { loadCompanies, loadCompaniesSuccess } from '../../store/actions/static.actions';
import { errorHandler } from 'src/app/store/effects/error-operator';
import { error } from 'src/app/store/actions/view.actions';
import { deleteCache } from 'src/ngforage/storage-operator';
import { NgForageCache } from 'ngforage';
import { ngfRootOptions } from 'src/ngforage/ngforage';


@Injectable()
export class CompanyEffects {

  filter$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.filterCompany),
    map(({ filters }) => navigate({ commands: [], extras: { queryParams: filters } }))
  ));

  save$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.saveCompany),
    switchMap(({ company }) => this.service.save(company).pipe(
      map(() => CompanyActions.saveCompanySuccess()),
      errorHandler
    ))
  ));

  verify$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.verifyCompany),
    switchMap(({ company }) => this.service.verify(company).pipe(
      map(() => CompanyActions.saveCompanySuccess()),
      errorHandler
    ))
  ));

  cancel$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.cancelCompany),
    switchMap(({ company }) => this.service.cancel(company).pipe(
      map(() => CompanyActions.saveCompanySuccess()),
      errorHandler
    ))
  ));

  saved$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.saveCompanySuccess),
    tap(deleteCache('companies', this.cache)),
    map(() => loadCompanies())
  ));

  loading$ = createEffect(() => this.actions$.pipe(
    ofType(
      CompanyActions.saveCompany,
      CompanyActions.cancelCompany,
      CompanyActions.verifyCompany,
      loadCompanies
    ),
    map(() => CompanyActions.companyLoading({ loading: true }))
  ));

  loadingDone$ = createEffect(() => this.actions$.pipe(
    ofType(CompanyActions.saveCompanySuccess, loadCompaniesSuccess, error),
    map(() => CompanyActions.companyLoading({ loading: false }))
  ));

  constructor(
    private actions$: Actions, 
    private service: CompanyService,
    private cache: NgForageCache) {      
      cache.configure(ngfRootOptions);
  }

}
