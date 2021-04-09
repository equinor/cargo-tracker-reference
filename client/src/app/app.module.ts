import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';

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
import { MinimalRouterStateSerializer, NavigationActionTiming, StoreRouterConnectingModule } from '@ngrx/router-store';
import { ViewEffects } from './store/effects/view.effects';
import { StaticService } from './static.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BASE_URL } from './tokens';
import {
  MSAL_GUARD_CONFIG,
  MSAL_INSTANCE,
  MSAL_INTERCEPTOR_CONFIG,
  MsalBroadcastService,
  MsalGuardConfiguration,
  MsalInterceptor,
  MsalInterceptorConfiguration,
  MsalModule,
  MsalRedirectComponent,
  MsalService
} from '@azure/msal-angular';
import { MatButtonModule } from '@angular/material/button';
import { MAT_LABEL_GLOBAL_OPTIONS } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { RouterEffects } from './store/effects/router.effects';
import { ErrorHandlerModule } from '@ngx-stoui/error-handler';
import { NavigationModule } from './navigation/navigation.module';
import { USE_HASH_ROUTING } from '@ngx-stoui/drawer';
import { AppInsightsService } from './app-insights/app-insights.service';
import { ErrorHandlerService } from './error-handler.service';
import { InstanceFactory, NgForageCache, NgForageConfig } from 'ngforage';
import { InteractionType, IPublicClientApplication } from '@azure/msal-browser';
import { msalConfig } from '../msal';
import { RefMsalInterceptor } from './msal-interceptor';

export function MSALInstanceFactory(): IPublicClientApplication {
  return msalConfig;
}

export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
  };
}

export function MSALInterceptorConfigFactory(): MsalInterceptorConfiguration {
  const protectedResourceMap = new Map<string, Array<string>>()
    .set('/ctref/*', [ 'openid' ])
    .set('/ctintegration/*', [ 'openid' ]);
  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap
  };
}


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NavigationModule,
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
    EffectsModule.forRoot([ StaticEffects, ViewEffects, RouterEffects ]),
    StoreRouterConnectingModule.forRoot({ serializer: MinimalRouterStateSerializer,
      navigationActionTiming: NavigationActionTiming.PostActivation
    }),
    MsalModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    ErrorHandlerModule,
  ],
  providers: [
    AppInsightsService,
    MsalService,
    MsalBroadcastService,
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory
    },
    {
      provide: MSAL_INTERCEPTOR_CONFIG,
      useFactory: MSALInterceptorConfigFactory
    },
    MsalInterceptor,
    { provide: HTTP_INTERCEPTORS, useClass: RefMsalInterceptor, multi: true },
    StaticService,
    { provide: NAVIGATION_HOME_ICON, useValue: { icon: 'apps', text: 'Reference data' } },
    { provide: BASE_URL, useValue: '/ctref' },
    { provide: MAT_LABEL_GLOBAL_OPTIONS, useValue: { float: 'always' } },
    { provide: USE_HASH_ROUTING, useValue: false },
    { provide: ErrorHandler, useClass: ErrorHandlerService },
    {
      provide: NgForageCache,
      useFactory: (ngForageConfig: NgForageConfig) =>
        // @ts-ignore
        new NgForageCache({}, new InstanceFactory(ngForageConfig)),
      deps: [
        NgForageConfig,
      ]
    },
  ],
  bootstrap: [AppComponent, MsalRedirectComponent]
})
export class AppModule {
}
