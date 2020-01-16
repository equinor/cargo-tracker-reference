import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TerminalsComponent } from './terminals.component';


const routes: Routes = [
  { path: '', component: TerminalsComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class TerminalsRoutingModule {
}
