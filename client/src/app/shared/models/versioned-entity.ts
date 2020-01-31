export interface VersionedEntity {
  id?: string;
  version?: number;
  name?: string;
  cancelled: boolean;
}
