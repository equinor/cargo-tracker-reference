import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompaniesRoutingModule } from './companies-routing.module';
import { CompaniesComponent } from './companies.component';
import { CompanyFilterComponent } from './company-filter/company-filter.component';
import { CompanyListComponent } from './company-list/company-list.component';
import { StoFilterPanelModule } from '@ngx-stoui/common';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { StoFormModule, StoSlideToggleModule } from '@ngx-stoui/form';
import { StoDirectivesModule } from '@ngx-stoui/core';
import { StoDatatableModule } from '@ngx-stoui/datatable';
import { SharedModule } from '../shared/shared.module';
import { EffectsModule } from '@ngrx/effects';
import { CompanyEffects } from './store/company.effects';
import { StoreModule } from '@ngrx/store';
import * as fromCompany from './store/company.reducer';
import { CompanyService } from './company.service';


@NgModule({
  declarations: [
    CompaniesComponent,
    CompanyFilterComponent,
    CompanyListComponent,
  ],
  imports: [
    CommonModule,
    CompaniesRoutingModule,
    StoFilterPanelModule,
    MatChipsModule,
    MatIconModule,
    ReactiveFormsModule,
    StoFormModule,
    MatSelectModule,
    StoDirectivesModule,
    MatDialogModule,
    StoSlideToggleModule,
    StoDatatableModule,
    MatInputModule,
    SharedModule,
    MatButtonModule,
    MatCheckboxModule,
    EffectsModule.forFeature([ CompanyEffects ]),
    StoreModule.forFeature(fromCompany.companyFeatureKey, fromCompany.reducer),
    MatAutocompleteModule
  ],
  providers: [
    CompanyService
  ],
  entryComponents: [
  ]
})
export class CompaniesModule {
}
