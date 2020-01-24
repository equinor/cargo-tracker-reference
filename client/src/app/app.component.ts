import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { loadViews } from './store/actions/view.actions';
import { MsalService } from '@azure/msal-angular';
import { loadCompanies, loadCountries, loadGrades, loadRegions, loadTerminals } from './store/actions/static.actions';
import { Router } from '@angular/router';
import { User } from 'msal';
import { Observable } from 'rxjs';
import { Breadcrumb } from '@ngx-stoui/common';
import { selectRouteData, selectRouteTitle } from './store/selectors/router.selectors';

const modules = [
  { routerLink: [ '/', 'grades' ], label: 'Grades', sort: 0 },
  // { routerLink: [ '/', 'regions' ], label: 'Regions', sort: 1 },
  { routerLink: [ '/', 'countries' ], label: 'Countries', sort: 2 },
  { routerLink: [ '/', 'terminals' ], label: 'Terminals', sort: 3 },
  { routerLink: [ '/', 'companies' ], label: 'Companies', sort: 4 },
];


@Component({
  selector: 'ctref-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.scss' ]
})
export class AppComponent implements OnInit {
  public user: User;
  public breadCrumbs$: Observable<Breadcrumb[]>;
  home = {
    command: () => this.router.navigate([ '/' ])
  };

  constructor(private store: Store<any>, private msal: MsalService, private router: Router) {
  }

  ngOnInit() {
    this.store.dispatch(loadViews({ modules }));
    this.store.dispatch(loadGrades());
    this.store.dispatch(loadCountries());
    this.store.dispatch(loadTerminals());
    this.store.dispatch(loadRegions());
    this.store.dispatch(loadCompanies());
    this.user = this.msal.getUser();
    this.breadCrumbs$ = this.store
      .pipe(select(selectRouteTitle));
  }

  async login() {
    // https://StatoilSRM.onmicrosoft.com/ea9b9ba9-7231-450f-9fba-7704d9d6400a/Deal.read
    // https://StatoilSRM.onmicrosoft.com/a4d8d583-9bc8-47bf-b159-023cde0446ec/Cargo.Delivery.Read
    const scopes = [ 'https://StatoilSRM.onmicrosoft.com/40f7d557-702f-4f94-ab32-a476fb5927a0/user_impersonation' ];
    try {
      await this.msal.acquireTokenSilent(scopes);
    } catch {
      await this.msal.loginRedirect(scopes);
    }
  }

  logout() {
    this.msal.logout();
  }
}

