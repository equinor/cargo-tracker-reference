import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { config } from './msal';
import { MSAL_CONFIG } from '@azure/msal-angular';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic([{ provide: MSAL_CONFIG, useValue: config }]).bootstrapModule(AppModule)
  .catch(err => console.error(err));
