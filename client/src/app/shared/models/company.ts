import { VersionedEntity } from './versioned-entity';

export interface Company extends VersionedEntity {
  name: string;
  aliases: string[];
  verified?: boolean;

  alias?: string;
  originalAlias?: string;
  originalName?: string;
  edit?: boolean;
  addAlias?: boolean;
  root?: string;
}
