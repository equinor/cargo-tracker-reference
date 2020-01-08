import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { ViewEffects } from './view.effects';

describe('ViewEffects', () => {
  let actions$: Observable<any>;
  let effects: ViewEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ViewEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.get<ViewEffects>(ViewEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
