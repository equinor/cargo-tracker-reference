import { AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, TemplateRef, ViewChild } from '@angular/core';
import { Company } from '../../shared/models/company';
import { Column } from '@ngx-stoui/datatable';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { TableChips } from '../../shared/table';

@Component({
  selector: 'ctref-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: [ './company-list.component.scss' ]
})
export class CompanyListComponent extends TableChips<Company> implements AfterViewInit, OnChanges {
  @Input()
  rows: Company[];
  @Input()
  loading: boolean;
  @Output()
  verify = new EventEmitter<Company>();
  @Output()
  cancel = new EventEmitter<Company>();
  public columns: Column[];

  @ViewChild('aliasTmpl', { static: true })
  aliasTmpl: TemplateRef<any>;
  @ViewChild('verifiedTmpl', { static: true })
  verifiedTmpl: TemplateRef<any>;
  @ViewChild('actionsTmpl', { static: true })
  actionsTmpl: TemplateRef<any>;
  @ViewChild('nameTmpl', { static: true })
  nameTmpl: TemplateRef<any>;

  ngAfterViewInit(): void {
    const getEditCellClass = (value, column, row: Company) => row.cancelled ? '' : 'edit-cell';
    requestAnimationFrame(() => this.columns = [
      {
        prop: 'name',
        name: 'Name',
        flexGrow: 0,
        flexBasis: 200,
        flexShrink: 0,
        cellTemplate: this.nameTmpl,
        cellClass: getEditCellClass as any
      },
      { prop: 'aliases', name: 'Aliases', cellTemplate: this.aliasTmpl, cellClass: getEditCellClass as any },
      { prop: 'verified', name: 'Verified', flexGrow: 0, flexBasis: 80, cellTemplate: this.verifiedTmpl, flexShrink: 0 },
      { prop: '', name: '', flexGrow: 0, flexBasis: 80, cellTemplate: this.actionsTmpl, flexShrink: 0, disableSort: true },
    ]);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ( changes.rows && changes.rows.currentValue ) {
      this.editing = {};
    }
  }

  public rowClass(row: Company) {
    return row.cancelled ? 'cancelled' : '';
  }

  public verifiedChange(event: MatCheckboxChange, row: Company) {
    const editing = this.editing[ row.id ] || { ...row };
    editing.verified = event.checked;
    this.verify.emit(editing);
  }

  public onValueChange(value: any, row: Company, event?: KeyboardEvent) {
    if ( ( event && event.key === 'Enter' ) ) {
      this.save.emit(this.editing[ row.id ]);
      return;
    } else if ( event && event.key === 'Escape' ) {
      if ( !row.id ) {
        this.cancel.emit(row);
      }
      return;
    }
    if ( this.editing[ row.id ] ) {
      this.editing[ row.id ] = { ...row, ...this.editing[ row.id ] };
    } else {
      this.editing[ row.id ] = { ...row };
    }
    this.editing[ row.id ].name = value;
  }
}
