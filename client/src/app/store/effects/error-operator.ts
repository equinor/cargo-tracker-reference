import * as ViewActions from '../actions/view.actions';
import { catchError } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

export const errorHandler = catchError<any, Observable<any>>(error => of(ViewActions.error({error})));
