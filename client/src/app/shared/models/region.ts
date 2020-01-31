import { VersionedEntity } from './versioned-entity';
import { Country } from './location';

export interface Region extends VersionedEntity {
  name: string;
  active: boolean;
  updatedBy: string;
  updatedDateTime: Date | string;
}

export interface RegionWithCountries extends Region {
  countries: Country[];
}
