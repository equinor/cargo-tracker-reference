// todo: Tests

import { getSortCountryFn, sortVerified } from './sorters';
import { Grade } from '../../shared/models/grade';

describe('GradeSorters', () => {
  describe('getSortCountryFn', () => {
    const countries = [ { id: '0', name: 'A' }, { id: '1', name: 'B' }, { id: '2', name: 'C' }, { id: '3', name: 'D' }, {
      id: '4',
      name: 'E'
    } ] as any;
    const list = [ { id: '0', countryId: '4' }, { id: '1', countryId: '1' }, { id: '2', countryId: '3' }, {
      id: '3',
      countryId: '0'
    } ] as Grade[];
    it('should sort the rows by country name, not id', () => {
      const sorted = [ ...list ].sort(getSortCountryFn(countries)) as Grade[];
      expect(sorted[ 0 ]).toBe(list[ 3 ]);
    });
  });
  describe('sortVerified', () => {
    const list = [ { id: '0', verified: false }, { id: '1', verified: false }, { id: '2', verified: true }, {
      id: '3',
      verified: false
    } ] as any;
    it('should sort the rows where verified = true is at the end', () => {
      const sorted = [ ...list ].sort(sortVerified);
      expect(sorted[ 3 ]).toBe(list[ 2 ]);
    });
  });
});
