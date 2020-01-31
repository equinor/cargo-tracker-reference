import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl } from '@angular/forms';
import { CompanyService } from '../company.service';
import { Observable } from 'rxjs';
import { Company } from '../../shared/models/company';

@Component({
  selector: 'ctref-merge-company',
  templateUrl: './merge-company.component.html',
  styleUrls: [ './merge-company.component.scss' ]
})
export class MergeCompanyComponent implements OnInit {
  public control = new FormControl();
  public count$: Observable<number>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { company: Company; companies: Company[] },
    private service: CompanyService
  ) {
  }

  public displayFn(c: Company) {
    return c ? c.name : '';
  }

  ngOnInit(): void {
    this.count$ = this.service.count(this.data.company);
  }

}
