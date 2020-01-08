import { AbstractControlOptions, FormBuilder, FormGroup } from '@angular/forms';
import { AfterViewInit, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Observable, OperatorFunction } from 'rxjs';
import { startWith, tap } from 'rxjs/operators';

export interface FilterList {
  key: string;
  value: string;
}

export abstract class FilterForm<T> implements OnInit {
  abstract formConfig: { [ key: string ]: any };
  abstract serializer: OperatorFunction<T, FilterList[]>;
  @Output()
  filterChanged = new EventEmitter<T>();
  @Input()
  value: T;

  public form: FormGroup;
  public filter$: Observable<FilterList[]>;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.form = this.fb.group(this.formConfig);
    this.filter$ = this.form
      .valueChanges
      .pipe(tap(console.log), startWith(this.form.value), this.serializer);
  }

  public clearFilter(key: string) {
    this.form.get(key).reset();
  }

  public toggled() {
    setTimeout(() => window.dispatchEvent(new Event('resize')), 300);
  }

}
