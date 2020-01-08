import { VersionedEntity } from './versioned-entity';

export interface Grade extends VersionedEntity {
  reference?: string;
  name: string;
  api: number;
  sulphur: number;
  source: string;
  ocdName: string;
  countryId?: string;
  validFrom?: Date | string;
  tradingAreaId: string;
  updatedDateTime: Date | string;
  updatedBy: string;
  active: boolean;
  verified: boolean;
  historicAnalyses: Analysis[];
}

export interface Analysis {
  api: number;
  fromDate: Date;
  toDate: Date;
  id: string;
  sulphur: number;
  updatedBy?: string;
  updatedDateTime?: string;
  version?: number;
}
