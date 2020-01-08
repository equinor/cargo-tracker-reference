import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { GradeEffects } from './grade.effects';

describe('GradeEffects', () => {
  let actions$: Observable<any>;
  let effects: GradeEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        GradeEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.get<GradeEffects>(GradeEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
