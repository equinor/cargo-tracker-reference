import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CountriesRoutingModule } from './countries-routing.module';
import { CountriesComponent } from './countries.component';
import { CountryFilterComponent } from './country-filter/country-filter.component';
import { CountryListComponent } from './country-list/country-list.component';
import { EffectsModule } from '@ngrx/effects';
import { CountryEffects } from './store/country.effects';
import * as fromCountry from './store/country.reducer';
import { StoreModule } from '@ngrx/store';
import { StoFormModule } from '@ngx-stoui/form';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { StoDirectivesModule } from '@ngx-stoui/core';
import { StoFilterPanelModule } from '@ngx-stoui/common';
import { StoDatatableModule } from '@ngx-stoui/datatable';
import { CountryService } from './country.service';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [ CountriesComponent, CountryFilterComponent, CountryListComponent ],
  imports: [
    CommonModule,
    CountriesRoutingModule,
    EffectsModule.forFeature([ CountryEffects ]),
    StoreModule.forFeature(fromCountry.countryFeatureKey, fromCountry.reducer),
    StoFilterPanelModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    StoDirectivesModule,
    MatSelectModule,
    MatFormFieldModule,
    StoFormModule,
    StoDatatableModule,
    SharedModule,
  ],
  providers: [ CountryService ]
})
export class CountriesModule {
}
