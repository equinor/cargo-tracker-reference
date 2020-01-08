import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { StaticEffects } from './static.effects';

describe('StaticEffects', () => {
  let actions$: Observable<any>;
  let effects: StaticEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        StaticEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.get<StaticEffects>(StaticEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
