import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompaniesComponent } from './companies.component';
import { MemoizedSelector, Store } from '@ngrx/store';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MatDialogModule } from '@angular/material';
import { Company } from '../shared/models/company';
import * as companiesState from './store/company.reducer';
import * as fromCompanies from './store/company.selectors';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import Spy = jasmine.Spy;


describe('CompaniesComponent', () => {
  let component: CompaniesComponent;
  let fixture: ComponentFixture<CompaniesComponent>;
  let store: MockStore<companiesState.State>;
  let dispatchSpy: Spy;
  let mockSelectCompanies: MemoizedSelector<companiesState.State, any>;
  let mockSelectCompanyFilter: MemoizedSelector<companiesState.State, any>;
  let mockSelectCompanyLoading: MemoizedSelector<companiesState.State, any>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        imports: [ MatDialogModule ],
        declarations: [ CompaniesComponent ],
        schemas: [ NO_ERRORS_SCHEMA ],
        providers: [ provideMockStore() ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompaniesComponent);
    component = fixture.componentInstance;
    store = TestBed.get<Store<any>>(Store);
    dispatchSpy = spyOn(store, 'dispatch').and.callThrough();
    mockSelectCompanies = store.overrideSelector(fromCompanies.selectCompanies, []);
    mockSelectCompanyFilter = store.overrideSelector(fromCompanies.selectCompanyFilter, {});
    mockSelectCompanyLoading = store.overrideSelector(fromCompanies.selectCompanyLoading, false);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dispatch filter action', () => {
    component.onFilterChanged({ cancelled: true, verified: true });
    expect(dispatchSpy).toHaveBeenCalledTimes(1);
  });

  it('should dispatch save action', () => {
    component.save({} as Company);
    expect(dispatchSpy).toHaveBeenCalledTimes(1);
  });

  it('should dispatch verify action', () => {
    component.verify({} as Company);
    expect(dispatchSpy).toHaveBeenCalledTimes(1);
  });

  it('should dispatch cancel action if company has id', () => {
    component.cancel({ id: 'aghs' } as Company);
    expect(dispatchSpy).toHaveBeenCalledTimes(1);
  });

  it('should set row$ to null when removing and id is null', () => {
    expect(component.row$.value).toBeNull();
    component.add();
    fixture.detectChanges();
    const val = component.row$.value;
    expect(val).toBeDefined();
    component.cancel(val);
    fixture.detectChanges();
    expect(component.row$.value).toBeNull();
    expect(dispatchSpy).toHaveBeenCalledTimes(0);
  });

  it('should update row$ when adding', () => {
    expect(component.row$.value).toBeNull();
    component.add();
    fixture.detectChanges();
    const val = component.row$.value;
    expect(val).toBeDefined();
  });

});
