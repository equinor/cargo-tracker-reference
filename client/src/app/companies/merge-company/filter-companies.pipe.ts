import { Pipe, PipeTransform } from '@angular/core';
import { Company } from '../../shared/models/company';

@Pipe({
  name: 'filterCompanies'
})
export class FilterCompaniesPipe implements PipeTransform {

  transform(companies: Company[], query: string): any {
    if ( query ) {
      const re = new RegExp(query, 'i');
      return companies
        .filter(c => re.test(c.name));
    }
    return companies;
  }

}
