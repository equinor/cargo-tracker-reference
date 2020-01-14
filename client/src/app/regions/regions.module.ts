import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegionsRoutingModule } from './regions-routing.module';
import { RegionsComponent } from './regions.component';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material';
import { DragDropModule } from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [ RegionsComponent ],
  imports: [
    CommonModule,
    RegionsRoutingModule,
    MatCardModule,
    MatListModule,
    DragDropModule
  ]
})
export class RegionsModule {
}
