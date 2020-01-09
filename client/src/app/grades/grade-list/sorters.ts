import { Country } from '../../shared/models/location';
import { Grade } from '../../shared/models/grade';

export function getSortCountryFn(countries: Country[]) {
  return (currentRow: Grade, nextRow: Grade) => {
    const currentId = currentRow.countryId;
    const nextId = nextRow.countryId;
    const country = countries.find(c => c.id === currentId);
    const nextCountry = countries.find(c => c.id === nextId);
    if ( country && nextCountry ) {
      return country.name.localeCompare(nextCountry.name);
    }
    if ( !currentId ) {
      return -1;
    } else if ( !nextId ) {
      return 1;
    }
  };
}

export const sortVerified = (currentRow: Grade, nextRow: Grade) => {
  const curr = currentRow.verified ? 1 : -1;
  const next = nextRow.verified ? 1 : -1;
  return curr - next;
};
