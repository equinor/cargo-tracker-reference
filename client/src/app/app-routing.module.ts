import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MsalGuard } from '@azure/msal-angular';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'callback', component: HomeComponent },
  { path: 'grades', canActivate: [ MsalGuard ], loadChildren: () => import('./grades/grades.module').then(m => m.GradesModule) },
  { path: 'regions', canActivate: [ MsalGuard ], loadChildren: () => import('./regions/regions.module').then(m => m.RegionsModule) },
  {
    path: 'countries',
    canActivate: [ MsalGuard ],
    loadChildren: () => import('./countries/countries.module').then(m => m.CountriesModule)
  },
  {
    path: 'terminals',
    canActivate: [ MsalGuard ],
    loadChildren: () => import('./terminals/terminals.module').then(m => m.TerminalsModule)
  },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
