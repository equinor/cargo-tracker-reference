import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GradesRoutingModule } from './grades-routing.module';
import { GradesComponent } from './grades.component';
import { StoDatatableModule } from '@ngx-stoui/datatable';
import { StoFilterPanelModule } from '@ngx-stoui/common';
import { MatButtonModule } from '@angular/material/button';
import { GradeFilterComponent } from './grade-filter/grade-filter.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule, MatIconModule, MatSelectModule } from '@angular/material';
import { StoDirectivesModule } from '@ngx-stoui/core';
import { StoFormModule, StoSlideToggleModule } from '@ngx-stoui/form';
import { GradeService } from './grade.service';
import { StoreModule } from '@ngrx/store';
import * as fromGrade from './store/grade.reducer';
import { EffectsModule } from '@ngrx/effects';
import { GradeEffects } from './store/grade.effects';


@NgModule({
  declarations: [ GradesComponent, GradeFilterComponent ],
  imports: [
    CommonModule,
    GradesRoutingModule,
    StoDatatableModule,
    StoFilterPanelModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    StoDirectivesModule,
    MatSelectModule,
    MatFormFieldModule,
    StoSlideToggleModule,
    StoFormModule,
    StoreModule.forFeature(fromGrade.gradeFeatureKey, fromGrade.reducer),
    EffectsModule.forFeature([GradeEffects]),
  ],
  providers: [
    GradeService
  ]
})
export class GradesModule {
}
