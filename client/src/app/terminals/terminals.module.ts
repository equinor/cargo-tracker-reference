import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TerminalsRoutingModule } from './terminals-routing.module';
import { TerminalsComponent } from './terminals.component';
import { EffectsModule } from '@ngrx/effects';
import { TerminalsEffects } from './store/terminals.effects';
import { StoreModule } from '@ngrx/store';
import * as fromTerminals from './store/terminals.reducer';
import { TerminalFilterComponent } from './terminal-filter/terminal-filter.component';
import { TerminalListComponent } from './terminal-list/terminal-list.component';
import { StoFilterPanelModule } from '@ngx-stoui/common';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { StoFormModule, StoSlideToggleModule } from '@ngx-stoui/form';
import { StoDirectivesModule } from '@ngx-stoui/core';
import { StoDatatableModule } from '@ngx-stoui/datatable';
import { TerminalsService } from './terminals.service';
import { NameFromIdPipe } from './terminal-list/name-from-id.pipe';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ TerminalsComponent, TerminalFilterComponent, TerminalListComponent, NameFromIdPipe ],
  imports: [
    CommonModule,
    TerminalsRoutingModule,
    StoreModule.forFeature(fromTerminals.terminalsFeatureKey, fromTerminals.reducer),
    EffectsModule.forFeature([ TerminalsEffects ]),
    StoFilterPanelModule,
    MatChipsModule,
    MatIconModule,
    ReactiveFormsModule,
    StoFormModule,
    MatSelectModule,
    StoDirectivesModule,
    StoSlideToggleModule,
    StoDatatableModule,
    MatInputModule,
    SharedModule,
  ],
  providers: [
    TerminalsService
  ]
})
export class TerminalsModule {
}
