import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { loadViews } from './store/actions/view.actions';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { loadCompanies, loadCountries, loadEnvironment, loadGrades, loadRegions, loadTerminals } from './store/actions/static.actions';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { Breadcrumb } from '@ngx-stoui/common';
import { selectRouteTitle } from './store/selectors/router.selectors';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material/icon';
import { selectEnvironment } from './store/selectors/static.selectors';
import { AccountInfo } from '@azure/msal-common';
import { AuthenticationResult, EventMessage, EventType, InteractionStatus } from '@azure/msal-browser';
import { filter, take } from 'rxjs/operators';

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
  public user: AccountInfo;
  public breadCrumbs$: Observable<Breadcrumb[]>;
  public environment$: Observable<any>;
  private destroyed$ = new Subject();


  constructor(
    private store: Store<any>,
    private msal: MsalService,
    private router: Router,
    private msalBroadcastService: MsalBroadcastService,
    private sanitizer: DomSanitizer,
    private iconRegistry: MatIconRegistry
  ) {
    this.iconRegistry.addSvgIcon('tnt', this.sanitizer.bypassSecurityTrustResourceUrl('assets/tnt.svg'));
  }

  ngOnInit() {
    this.user = this.msal.instance.getActiveAccount();
    // Set active account on login success
    this.msalBroadcastService.msalSubject$
      .pipe(
        filter((event: EventMessage) => [ EventType.LOGIN_SUCCESS, EventType.ACQUIRE_TOKEN_SUCCESS ].includes(event.eventType)),
        take(1))
      .subscribe(loginEvent => {
        const payload = loginEvent.payload as AuthenticationResult;
        this.msal.instance.setActiveAccount(payload.account);
      });

    // Set user when msal interaction is done
    if ( !this.user ) {
      this.msalBroadcastService.inProgress$
        .pipe(
          filter((status: InteractionStatus) => status === InteractionStatus.None),
          take(1))
        .subscribe(() => this.checkAndSetActiveAccount());
    }

    this.breadCrumbs$ = this.store
      .pipe(select(selectRouteTitle));
    this.environment$ = this.store.pipe(select(selectEnvironment));
  }

  async checkAndSetActiveAccount() {
    /**
     * Set active account as user
     * If no active account set but there are accounts signed in, sets first account to active account
     * If not, try to login
     */
    const activeAccount = this.msal.instance.getActiveAccount();
    if ( activeAccount ) {
      this.user = activeAccount;
      this.init();
      return;
    }

    if ( this.msal.instance.getAllAccounts().length > 0 ) {
      const accounts = this.msal.instance.getAllAccounts();
      this.msal.instance.setActiveAccount(accounts[ 0 ]);
      this.user = this.msal.instance.getActiveAccount();
      this.init();
    } else {
      await this.login();
    }
  }

  async login() {
    try {
      await this.msal.instance.acquireTokenSilent({ scopes: [ 'openid' ] });
    } catch {
      await this.msal.instance.loginRedirect();
    }
  }
  logout() {
    this.msal.logout();
  }

  init() {
    this.store.dispatch(loadViews({ modules }));
    this.store.dispatch(loadGrades());
    this.store.dispatch(loadCountries());
    this.store.dispatch(loadTerminals());
    this.store.dispatch(loadRegions());
    this.store.dispatch(loadCompanies());
    this.store.dispatch(loadEnvironment());
  }

  navigate(commands: string[]) {
    this.router.navigate(commands)
      .catch(console.error);
  }
}

