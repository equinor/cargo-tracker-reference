import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MsalGuard } from '@azure/msal-angular';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'callback', component: HomeComponent },
  {
    path: 'grades',
    data: { title: 'Grades' },
    canActivate: [ MsalGuard ],
    loadChildren: () => import('./grades/grades.module').then(m => m.GradesModule)
  },
  {
    path: 'regions',
    data: { title: 'Regions' },
    canActivate: [ MsalGuard ],
    loadChildren: () => import('./regions/regions.module').then(m => m.RegionsModule)
  },
  {
    path: 'countries', data: { title: 'Countries' },
    canActivate: [ MsalGuard ],
    loadChildren: () => import('./countries/countries.module').then(m => m.CountriesModule)
  },
  {
    path: 'terminals', data: { title: 'Terminals' },
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
