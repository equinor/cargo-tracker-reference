import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { TerminalsEffects } from './terminals.effects';

describe('TerminalsEffects', () => {
  let actions$: Observable<any>;
  let effects: TerminalsEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TerminalsEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.get<TerminalsEffects>(TerminalsEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
