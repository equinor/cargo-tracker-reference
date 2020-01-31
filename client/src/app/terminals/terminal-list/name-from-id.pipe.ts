import { Pipe, PipeTransform } from '@angular/core';
import { VersionedEntity } from '../../shared/models/versioned-entity';

@Pipe({
  name: 'nameFromId'
})
export class NameFromIdPipe implements PipeTransform {

  transform(id: string, list: VersionedEntity[]): any {
    const ent = ( list || [] ).find(e => e.id === id);
    return ent ? ent.name : '';
  }

}
