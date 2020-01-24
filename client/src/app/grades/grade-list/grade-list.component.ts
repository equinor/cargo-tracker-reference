import { AfterViewInit, Component, EventEmitter, Input, Output, TemplateRef, ViewChild } from '@angular/core';
import { Grade } from '../../shared/models/grade';
import { Column } from '@ngx-stoui/datatable';
import { Country } from '../../shared/models/location';
import { getSortCountryFn, sortVerified } from './sorters';

@Component({
  selector: 'ctref-grade-list',
  templateUrl: './grade-list.component.html',
  styleUrls: [ './grade-list.component.scss' ]
})
export class GradeListComponent implements AfterViewInit {
  @Input()
  rows: Grade[];
  @Input()
  countries: Country[];
  @Input()
  loading: boolean;
  @Output()
  save = new EventEmitter<Grade>();
  @Output()
  merge = new EventEmitter<Grade>();
  @Output()
  verify = new EventEmitter<Grade>();
  @Output()
  cancelGrade = new EventEmitter<Grade>();
  @Output()
  cancel = new EventEmitter<void>();
  @ViewChild('countryTmpl', { static: true })
  countryTmpl: TemplateRef<any>;
  @ViewChild('verifiedTmpl', { static: true })
  verifiedTmpl: TemplateRef<any>;
  @ViewChild('actionsTmpl', { static: true })
  actionsTmpl: TemplateRef<any>;
  @ViewChild('nameTmpl', { static: true })
  nameTmpl: TemplateRef<any>;
  @ViewChild('apiTmpl', { static: true })
  apiTmpl: TemplateRef<any>;
  @ViewChild('sulphurTmpl', { static: true })
  sulphurTmpl: TemplateRef<any>;
  @ViewChild('ocdNameTmpl', { static: true })
  ocdNameTmpl: TemplateRef<any>;

  public columns: Column[];
  public editGrade: Grade;

  constructor() {
  }

  ngAfterViewInit() {
    const editCellClassFn = (value, column, row: Grade) => {
      const edit = !row.id || row.source === 'Cargo Tracking';
      return edit && !row.cancelled ? 'edit-cell' : '';
    };
    requestAnimationFrame(() => {
      this.columns = [
        { prop: 'name', name: 'Name', flexGrow: 0, flexBasis: 120, cellTemplate: this.nameTmpl, cellClass: editCellClassFn as any },
        { prop: 'api', name: 'API', flexGrow: 0, flexBasis: 120, cellTemplate: this.apiTmpl, cellClass: editCellClassFn as any },
        {
          prop: 'sulphur',
          name: 'Sulphur',
          flexGrow: 0,
          flexBasis: 120,
          cellTemplate: this.sulphurTmpl,
          cellClass: editCellClassFn as any
        },
        { prop: 'source', name: 'Source system', flexGrow: 0, flexBasis: 120 },
        {
          prop: 'countryId',
          name: 'Country',
          flexGrow: 0,
          flexBasis: 120,
          cellTemplate: this.countryTmpl,
          sortFn: getSortCountryFn(this.countries) as any,
          cellClass: editCellClassFn as any
        },
        { prop: 'ocdName', name: 'OCD Name', flexGrow: 0, flexBasis: 120, cellTemplate: this.ocdNameTmpl, cellClass: 'edit-cell' },
        { prop: 'verified', name: 'Verified', flexGrow: 0, flexBasis: 120, sortFn: sortVerified as any, cellTemplate: this.verifiedTmpl },
        { prop: '', name: '', cellTemplate: this.actionsTmpl, cellClass: 'actions-cell' }
      ];
      window.dispatchEvent(new Event('resize'));
    });
  }

  getRowClass(row: Grade) {
    let css = '';
    if ( !row.id ) {
      css = 'add-grade';
    }
    if ( row.cancelled ) {
      css = 'cancelled';
    }
    return css;
  }

  onValueChange(value: any, row: Grade, column: Column, event?: KeyboardEvent) {
    if ( ( event && event.key === 'Enter' ) ) {
      this.saveGrade();
      return;
    } else if ( event && event.key === 'Escape' ) {
      if ( !row.id ) {
        this.cancel.emit();
      }
      return;
    }
    if ( this.editGrade && this.editGrade.id === row.id ) {
      this.editGrade = { ...row, ...this.editGrade };
    } else {
      this.editGrade = { ...row };
    }
    this.editGrade[ column.prop ] = value;
    if ( column.prop === 'countryId' && row.id ) {
      this.saveGrade();
    }
  }

  saveGrade() {
    this.save.emit(this.editGrade);
    this.editGrade = null;
  }

  verifyGrade(grade: Grade) {
    this.verify.emit({ ...grade, verified: !grade.verified });
  }

}
