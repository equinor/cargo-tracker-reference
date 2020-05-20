import { ApplicationInsights, Snippet } from '@microsoft/applicationinsights-web';
import { Injectable } from '@angular/core';
import { config } from 'src/appinsights';

@Injectable()
export class AppInsightsService {
  appInsights: ApplicationInsights;
  constructor() {
    this.appInsights = new ApplicationInsights({
      config: config
    });
    this.appInsights.loadAppInsights();    
    this.appInsights.trackPageView();
  }

  logPageView(name?: string, url?: string) { // option to call manually
    this.appInsights.trackPageView({
      name: name,
      uri: url
    });
  }

  logEvent(name: string, properties?: { [key: string]: any }) {
    this.appInsights.trackEvent({ name: name}, properties);
  }

  logMetric(name: string, average: number, properties?: { [key: string]: any }) {
    this.appInsights.trackMetric({ name: name, average: average }, properties);
  }

  logException(exception: Error, severityLevel?: number) {
    this.appInsights.trackException({exception, severityLevel});
  }

  logTrace(message: string, properties?: { [key: string]: any }) {
    this.appInsights.trackTrace({ message}, properties);
  }
}