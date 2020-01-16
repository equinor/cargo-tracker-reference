import * as fromTerminals from './terminals.reducer';
import { selectTerminalsState } from './terminals.selectors';

describe('Terminals Selectors', () => {
  it('should select the feature state', () => {
    const result = selectTerminalsState({
      [ fromTerminals.terminalsFeatureKey ]: {}
    });

    expect(result).toEqual({});
  });
});
