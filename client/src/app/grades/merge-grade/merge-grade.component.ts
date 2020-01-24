import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GradeService } from '../grade.service';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { Grade } from '../../shared/models/grade';

@Component({
  selector: 'ctref-merge-grade',
  templateUrl: './merge-grade.component.html',
  styleUrls: [ './merge-grade.component.scss' ]
})
export class MergeGradeComponent implements OnInit {
  public control = new FormControl();
  public count$: Observable<number>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { grade: Grade; grades: Grade[] },
    private service: GradeService
  ) {
  }

  ngOnInit() {
    this.count$ = this.service.count(this.data.grade);
  }

  displayFn(grade: Grade) {
    return grade ? grade.name : '';
  }

}
