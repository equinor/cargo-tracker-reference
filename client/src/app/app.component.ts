import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { loadViews } from './store/actions/view.actions';
import { MsalService } from '@azure/msal-angular';
import { loadCountries, loadGrades } from './store/actions/static.actions';
import { Router } from '@angular/router';
import { User } from 'msal';

const modules = [
  { routerLink: [ '/', 'grades' ], label: 'Grades', sort: 0 },
  { routerLink: [ '/', 'regions' ], label: 'Regions', sort: 1 },
  { routerLink: [ '/', 'countries' ], label: 'Countries', sort: 2 },
  { routerLink: [ '/', 'terminals' ], label: 'Terminals', sort: 3 },
];


@Component({
  selector: 'ctref-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.scss' ]
})
export class AppComponent implements OnInit {
  public user: User;
  public breadCrumbs = [
    // { label: 'CT Reference' },
    // { label: 'Reference' },
  ];
  home = {
    command: () => this.router.navigate(['/'])
  };

  constructor(private store: Store<any>, private msal: MsalService, private router: Router) {
  }

  ngOnInit() {
    this.store.dispatch(loadViews({ modules }));
    this.store.dispatch(loadGrades());
    this.store.dispatch(loadCountries());
    this.user = this.msal.getUser();
  }

  async login() {
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
