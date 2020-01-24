import { Pipe, PipeTransform } from '@angular/core';
import { Country } from '../../shared/models/location';

@Pipe({
  name: 'countryNameFromId'
})
export class CountryNameFromIdPipe implements PipeTransform {

  transform(id: string, countries: Country[]): string {
    const country = (countries || []).find(c => c.id === id);
    if (country) {
      return country.name;
    }
    return '';
  }

}
