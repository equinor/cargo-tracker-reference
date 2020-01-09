import { FormBuilder, FormGroup } from '@angular/forms';
import { EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Observable, OperatorFunction, Subject } from 'rxjs';
import { debounceTime, startWith, takeUntil } from 'rxjs/operators';

export interface FilterList {
  key: string;
  value: string;
}

export abstract class FilterForm<T> implements OnInit, OnDestroy {
  abstract formConfig: { [ key: string ]: any };
  abstract serializer: OperatorFunction<T, FilterList[]>;
  protected destroyed$ = new Subject();
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
    this.form.reset(this.value || {});
    this.filter$ = this.form
      .valueChanges
      .pipe(startWith(this.form.value), this.serializer);
    this.form.valueChanges
      .pipe(
        debounceTime(250),
        takeUntil(this.destroyed$)
      ).subscribe(value => this.filterChanged.emit(value));
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  public clearFilter(key: string) {
    this.form.get(key).reset();
  }

  public toggled() {
    setTimeout(() => window.dispatchEvent(new Event('resize')), 300);
  }

}
