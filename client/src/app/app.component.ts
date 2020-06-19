import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { loadViews } from './store/actions/view.actions';
import { MsalService } from '@azure/msal-angular';
import {
  loadCompanies,
  loadCountries,
  loadEnvironment,
  loadGrades,
  loadRegions,
  loadTerminals
} from './store/actions/static.actions';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Breadcrumb } from '@ngx-stoui/common';
import { selectRouteTitle } from './store/selectors/router.selectors';
import { Account } from 'msal';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material/icon';
import { selectEnvironment } from './store/selectors/static.selectors';

const modules = [
  { routerLink: [ '/', 'grades' ], label: 'Grades', sort: 0 },
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
  public user: Account;
  public breadCrumbs$: Observable<Breadcrumb[]>;
  public environment$: Observable<any>;

  constructor(
    private store: Store<any>,
    private msal: MsalService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private iconRegistry: MatIconRegistry
  ) {
  }

  ngOnInit() {
    this.handleAuthCallback();
    this.store.dispatch(loadViews({ modules }));
    this.store.dispatch(loadGrades());
    this.store.dispatch(loadCountries());
    this.store.dispatch(loadTerminals());
    this.store.dispatch(loadRegions());
    this.store.dispatch(loadCompanies());
    this.store.dispatch(loadEnvironment());
    this.user = this.msal.getAccount();
    this.breadCrumbs$ = this.store
      .pipe(select(selectRouteTitle));
    this.environment$ = this.store.pipe(select(selectEnvironment));
    this.iconRegistry.addSvgIcon('tops', this.sanitizer.bypassSecurityTrustResourceUrl('assets/tops.svg'));
  }

  private handleAuthCallback() {
    if (!sessionStorage.getItem('CTR_REDIR')) {
      sessionStorage.setItem('CTR_REDIR', window.location.href);
    }
    this.msal.handleRedirectCallback((authError, response) => {
      if (authError) {
        console.error('Redirect Error: ', authError.errorMessage);
        return;
      }
      const redir = sessionStorage.getItem('CTR_REDIR');
      sessionStorage.removeItem('CTR_REDIR');
      if (redir && !(/callback/.test(redir))) { // Ensure we dont end up in an infinite loop of redirects to /callback
        window.location.href = redir;
      } else {
        window.location.href = `${window.location.origin}/reference`;
      }
    });
  }

  async login() {
    try {
      await this.msal.acquireTokenSilent({});
    } catch {
      await this.msal.loginRedirect({});
    }
  }

  logout() {
    this.msal.logout();
  }

  navigate(commands: string[]) {
    this.router.navigate(commands)
      .catch(console.error);
  }
}

