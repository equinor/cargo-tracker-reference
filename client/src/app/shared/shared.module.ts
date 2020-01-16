import { NgModule } from '@angular/core';
import { LatLngPipe } from './lat-lng.pipe';

@NgModule({
  declarations: [ LatLngPipe ],
  exports: [ LatLngPipe ]
})
export class SharedModule {
}
