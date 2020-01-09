import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NAVIGATION_HOME_ICON, StoAppHeaderModule } from '@ngx-stoui/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { HomeComponent } from './home/home.component';
import { StoreModule } from '@ngrx/store';
import { metaReducers, reducers } from './store/reducers';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import { EffectsModule } from '@ngrx/effects';
import { StaticEffects } from './store/effects/static.effects';
import { NavigationActionTiming, StoreRouterConnectingModule } from '@ngrx/router-store';
import { ViewEffects } from './store/effects/view.effects';
import { StaticService } from './static.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BASE_URL } from './tokens';
import { MsalInterceptor, MsalModule } from '@azure/msal-angular';
import { MAT_LABEL_GLOBAL_OPTIONS, MatButtonModule, MatIconModule, MatMenuModule } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    StoAppHeaderModule,
    BrowserAnimationsModule,
    MatCardModule,
    HttpClientModule,
    StoreModule.forRoot(reducers, {
      metaReducers,
      runtimeChecks: {
        strictStateImmutability: true,
        strictActionImmutability: true,
      }
    }),
    !environment.production ? StoreDevtoolsModule.instrument() : [],
    EffectsModule.forRoot([ StaticEffects, ViewEffects ]),
    StoreRouterConnectingModule.forRoot({
      navigationActionTiming: NavigationActionTiming.PostActivation
    }),
    MsalModule.forRoot({
      clientID: '18f46b40-3cb3-4bd4-b530-ab95114adb99',
      authority: 'https://login.microsoftonline.com/3aa4a235-b6e2-48d5-9195-7fcf05b459b0',
      redirectUri: 'http://localhost:4200/callback',
      consentScopes: [ 'https://StatoilSRM.onmicrosoft.com/40f7d557-702f-4f94-ab32-a476fb5927a0/user_impersonation' ]
    }),
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
  ],
  providers: [
    StaticService,
    { provide: NAVIGATION_HOME_ICON, useValue: { icon: 'settings', text: 'CT Reference' } },
    { provide: BASE_URL, useValue: '/api/ct' },
    { provide: HTTP_INTERCEPTORS, useClass: MsalInterceptor, multi: true },
    { provide: MAT_LABEL_GLOBAL_OPTIONS, useValue: { float: 'always' } }
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
