import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { loadViews } from './store/actions/view.actions';
import { MsalService } from '@azure/msal-angular';
import { loadGrades } from './store/actions/static.actions';

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
  public breadCrumbs = [
    // { label: 'CT Reference' },
    // { label: 'Reference' },
  ];
  home = {
    command: () => {
    }
  };

  constructor(private store: Store<any>, private msal: MsalService) {
  }

  ngOnInit() {
    this.store.dispatch(loadViews({ modules }));
    this.store.dispatch(loadGrades());
    if (!this.msal.getUser()) {
      this.login();
    }
  }

  async login() {
    const u = await this.msal.loginPopup();
    console.log(u);
  }
}
