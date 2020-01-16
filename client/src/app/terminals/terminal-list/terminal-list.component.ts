import { AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, TemplateRef, ViewChild } from '@angular/core';
import { Country, Terminal } from '../../shared/models/location';
import { Column } from '@ngx-stoui/datatable';
import { MatChipInputEvent } from '@angular/material';
import { ENTER } from '@angular/cdk/keycodes';

@Component({
  selector: 'ctref-terminal-list',
  templateUrl: './terminal-list.component.html',
  styleUrls: [ './terminal-list.component.scss' ]
})
export class TerminalListComponent implements AfterViewInit, OnChanges {
  @Input()
  rows: Terminal[];
  @Input()
  countries: Country[];
  @Input()
  loading: boolean;
  @Output()
  save = new EventEmitter<Terminal>();
  @ViewChild('countryTmpl', { static: true })
  countryTmpl: TemplateRef<any>;
  @ViewChild('latLngTmpl', { static: true })
  latLngTmpl: TemplateRef<any>;
  @ViewChild('aliasTmpl', { static: true })
  aliasTmpl: TemplateRef<any>;
  public editing: { [ id: string ]: Terminal } = {};
  public columns: Column[];
  readonly separatorKeysCodes: number[] = [ ENTER ];

  trackFn = (index, terminal: Terminal) => terminal.id;

  ngAfterViewInit(): void {
    const sortCountry = (prevC, nextC) => {
      const prev = this.countries.find(c => c.id === prevC.countryId);
      const next = this.countries.find(c => c.id === nextC.countryId);
      if ( !prevC && !nextC ) {
        return 0;
      } else if ( !nextC || !next ) {
        return -1;
      } else if ( !prevC || !prev ) {
        return 1;
      }

      return prev.name.localeCompare(next.name);
    };
    requestAnimationFrame(() => this.columns = [
      { prop: 'name', name: 'Name', flexGrow: 0, flexBasis: 140 },
      { prop: 'countryId', name: 'Country', cellTemplate: this.countryTmpl, flexGrow: 0, flexBasis: 120, sortFn: sortCountry as any },
      { prop: 'latitude', name: 'Lat/lng', cellTemplate: this.latLngTmpl, flexGrow: 0, flexBasis: 140, disableSort: true },
      { prop: 'source', name: 'Source', flexGrow: 0, flexBasis: 80 },
      { prop: 'aliases', name: 'Aliases', cellTemplate: this.aliasTmpl },
    ]);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ( changes.rows && changes.rows.currentValue ) {
      this.editing = {};
    }
  }

  remove(value: string, row: Terminal) {
    const editing = this.editing[ row.id ] || { ...row };
    editing.aliases.splice(editing.aliases.indexOf(value), 1);
    editing.aliases = [ ...editing.aliases ];
    this.editing = {
      ...this.editing,
      [ editing.id ]: { ...editing }
    };
  }

  add(event: MatChipInputEvent, row: Terminal) {
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
