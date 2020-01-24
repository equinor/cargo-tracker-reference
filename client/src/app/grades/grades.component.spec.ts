import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemoizedSelector, Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { GradesComponent } from './grades.component';
import { State } from './store/grade.reducer';
import * as fromGrade from './store/grade.selectors';
import { MatDialogModule } from '@angular/material';
import * as fromStatic from '../store/selectors/static.selectors';
import * as staticState from '../store/reducers/static.reducer';
import { Grade } from '../shared/models/grade';
import { format, startOfMonth } from 'date-fns';
import { cancelGrade, filterGrade, saveGrade, uploadGrades, verifyGrade } from './store/grade.actions';
import Spy = jasmine.Spy;

describe('GradesComponent', () => {
  let component: GradesComponent;
  let fixture: ComponentFixture<GradesComponent>;
  let store: MockStore<any>;
  let dispatch: Spy;
  let selectGradeList: MemoizedSelector<State, any>;
  let selectFilter: MemoizedSelector<State, any>;
  let selectLoading: MemoizedSelector<State, any>;
  let selectCountries: MemoizedSelector<staticState.State, any>;
  let grade: Grade;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ GradesComponent ],
        imports: [ MatDialogModule ],
        schemas: [ NO_ERRORS_SCHEMA ],
        providers: [ provideMockStore() ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradesComponent);
    component = fixture.componentInstance;
    store = TestBed.get(Store);
    dispatch = spyOn(store, 'dispatch');
    grade = {} as Grade;
    grade.verified = false;
    grade.source = 'Cargo Tracking';
    grade.validFrom = format(startOfMonth(new Date()), 'YYYY-MM-DD');
    grade.id = null;
    grade.historicAnalyses = [];
    store = TestBed.get(Store);
    selectGradeList = store.overrideSelector(fromGrade.selectGradeList, null);
    selectFilter = store.overrideSelector(fromGrade.selectFilter, null);
    selectLoading = store.overrideSelector(fromGrade.selectLoading, null);
    selectCountries = store.overrideSelector(fromStatic.selectCountries, []);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add a grade to row$', () => {
    const spy = spyOn(component.row$, 'next');
    component.add();
    expect(spy).toHaveBeenCalledWith(grade);
  });

  it('should dispatch a save action', () => {
    const action = saveGrade({ grade });
    component.save(grade);
    expect(dispatch).toHaveBeenCalledWith(action);
  });

  it('should dispatch a verify action', () => {
    const action = verifyGrade({ grade });
    component.verify(grade);
    expect(dispatch).toHaveBeenCalledWith(action);
  });

  it('should dispatch a filter action', () => {
    const filter = { countryId: '1', verified: false, cancelled: true };
    const action = filterGrade({ filter });
    component.onFilterChange(filter);
    expect(dispatch).toHaveBeenCalledWith(action);
  });

  it('should dispatch a cancel grade action', () => {
    grade.id = '1';
    const action = cancelGrade({ grade });
    component.cancelGrade(grade);
    expect(dispatch).toHaveBeenCalledWith(action);
  });

  it('should dispatch a upload action', () => {
    const action = uploadGrades({ file: null });
    component.upload(null);
    expect(dispatch).toHaveBeenCalledWith(action);
  });

});
