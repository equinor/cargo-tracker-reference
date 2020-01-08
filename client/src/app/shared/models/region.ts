import { VersionedEntity } from './versioned-entity';

export interface Region extends VersionedEntity {
  name: string;
  active: boolean;
  updatedBy: string;
  updatedDateTime: Date | string;
}
