import { ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { VersionedEntity } from './models/versioned-entity';
import { EventEmitter, Output } from '@angular/core';
import { Column } from '@ngx-stoui/datatable';
import { Company } from './models/company';

interface Alias extends VersionedEntity {
  aliases: string[];
}

interface Minimal<T> {
  columns: Column[];
  editing: { [ id: string ]: T };
}

abstract class TableBase<T extends VersionedEntity> {
  abstract columns: Column[];
  trackFn = (index, company: Company) => company.id;
}

export abstract class TableChips<T extends Alias> extends TableBase<T> implements Minimal<T> {
  @Output()
  save = new EventEmitter();

  editing: { [ id: string ]: T };
  readonly separatorKeysCodes: number[] = [ ENTER ];

  remove(value: string, row: T) {
    const editing = this.editing[ row.id ] || { ...row };
    const aliases = [ ...editing.aliases ];
    aliases.splice(editing.aliases.indexOf(value), 1);
    editing.aliases = aliases;
    this.editing = {
      ...this.editing,
      [ editing.id ]: { ...editing }
    };
  }

  add(event: MatChipInputEvent, row: T) {
    const val = event.value;
    if ( !val ) {
      this.save.emit(this.editing[ row.id ]);
      return;
    }
    const editing = this.editing[ row.id ] || { ...row };
    if ( editing.aliases.includes(val) ) {
      return;
    }
    editing.aliases = [ ...editing.aliases, val ];
    this.editing = {
      ...this.editing,
      [ editing.id ]: { ...editing }
    };
    event.input.value = '';
  }
}
