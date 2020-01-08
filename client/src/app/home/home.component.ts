import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import * as fromView from '../store/selectors/view.selectors';
import { Observable } from 'rxjs';
import { ViewModule } from '../shared/models/module';
import { BroadcastService, MsalService } from '@azure/msal-angular';

@Component({
  selector: 'ctref-home',
  templateUrl: './home.component.html',
  styleUrls: [ './home.component.scss' ]
})
export class HomeComponent implements OnInit {
  public modules$: Observable<ViewModule[]>;

  constructor(private store: Store<any>, private msal: MsalService ) {
  }

  ngOnInit() {
    this.modules$ = this.store.pipe(select(fromView.selectModules));
  }
}
