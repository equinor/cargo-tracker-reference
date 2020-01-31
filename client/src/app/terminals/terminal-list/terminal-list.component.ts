import { AfterViewInit, Component, Input, OnChanges, SimpleChanges, TemplateRef, ViewChild } from '@angular/core';
import { Country, Terminal } from '../../shared/models/location';
import { Column } from '@ngx-stoui/datatable';
import { TableChips } from '../../shared/table';

@Component({
  selector: 'ctref-terminal-list',
  templateUrl: './terminal-list.component.html',
  styleUrls: [ './terminal-list.component.scss' ]
})
export class TerminalListComponent extends TableChips<Terminal> implements AfterViewInit, OnChanges {
  @Input()
  rows: Terminal[];
  @Input()
  countries: Country[];
  @Input()
  loading: boolean;
  @ViewChild('countryTmpl', { static: true })
  countryTmpl: TemplateRef<any>;
  @ViewChild('latLngTmpl', { static: true })
  latLngTmpl: TemplateRef<any>;
  @ViewChild('aliasTmpl', { static: true })
  aliasTmpl: TemplateRef<any>;
  public columns: Column[];

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

}
