import { Injectable, ErrorHandler } from '@angular/core';
import { AppInsightsService } from './app-insights/app-insights.service';
import { SeverityLevel } from '@microsoft/applicationinsights-web';

@Injectable()
export class ErrorHandlerService extends ErrorHandler{

  constructor(private appInsightService: AppInsightsService) {
    super();
}

  handleError(error: Error) {
    const ignoreErrors = [ 'Error retrieving icon :tops!', 'ResizeObserver loop limit exceeded'];
    if ( !ignoreErrors.find(errorText => error.message.startsWith(errorText))) {
      this.appInsightService.logException(error, SeverityLevel.Error); // Manually log exception
    }
    super.handleError(error);
  }
}
