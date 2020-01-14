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
import { MatChipsModule, MatFormFieldModule, MatIconModule, MatSelectModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { StoDirectivesModule } from '@ngx-stoui/core';
import { StoFilterPanelModule } from '@ngx-stoui/common';
import { StoDatatableModule } from '@ngx-stoui/datatable';
import { LatLngPipe } from './country-list/lat-lng.pipe';
import { CountryService } from './country.service';


@NgModule({
  declarations: [ CountriesComponent, CountryFilterComponent, CountryListComponent, LatLngPipe ],
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
  ],
  providers: [ CountryService ]
})
export class CountriesModule {
}
