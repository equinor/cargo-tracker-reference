import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { Country } from '../../shared/models/location';
import { Region } from '../../shared/models/region';
import { Column } from '@ngx-stoui/datatable';
import { MatCheckboxChange, MatSelectChange } from '@angular/material';

@Component({
  selector: 'ctref-country-list',
  templateUrl: './country-list.component.html',
  styleUrls: [ './country-list.component.scss' ]
})
export class CountryListComponent implements AfterViewInit {
  @Input()
  rows: Country[];
  @Input()
  regions: Region[];

  @Output()
  save = new EventEmitter<Country>();

  public columns: Column[];
  @ViewChild('latLngTmpl', { static: true })
  latLngTmpl: TemplateRef<any>;
  @ViewChild('regionTmpl', { static: true })
  regionTmpl: TemplateRef<any>;

  trackFn = (index, row) => row.id;

  ngAfterViewInit() {
    requestAnimationFrame(() => this.columns = [
      { prop: 'name', name: 'Name', flexShrink: 0, flexBasis: 150 },
      { prop: 'latitude', name: 'Lat/Lng', flexShrink: 0, cellTemplate: this.latLngTmpl, flexBasis: 180 },
      { prop: 'regionId', name: 'Region', flexShrink: 0, cellTemplate: this.regionTmpl, cellClass: 'edit-cell', flexBasis: 150 },
      { prop: '', name: '', flexGrow: 1, flexShrink: 10, flexBasis: 400 },
    ]);
  }

  onSelectCountry(event: MatSelectChange, country: Country) {
    const updated = {...country, regionId: event.value};
    this.save.emit(updated);
  }
}
