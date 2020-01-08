import { VersionedEntity } from './versioned-entity';

export interface RegionLocation extends VersionedEntity {
  name: string;
  latitude: number;
  longitude: number;
  updatedDateTime?: Date | string;
  updatedBy?: string;
}

export interface Country extends RegionLocation {
  regionId: string;
}

export interface Terminal extends RegionLocation {
  countryId: string;
  source: string;
}
