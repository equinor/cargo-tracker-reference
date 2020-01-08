import * as fromStatic from '../reducers/static.reducer';
import { selectStaticState } from './static.selectors';

describe('Static Selectors', () => {
  it('should select the feature state', () => {
    const result = selectStaticState({
      [fromStatic.staticFeatureKey]: {}
    });

    expect(result).toEqual({});
  });
});
