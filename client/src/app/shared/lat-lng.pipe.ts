import { Pipe, PipeTransform } from '@angular/core';
import { Country } from './models/location';

@Pipe({
  name: 'latLng'
})
export class LatLngPipe implements PipeTransform {

  transform(value: Country): any {
    if (value && value.latitude && value.longitude) {
      const {latitude: lat, longitude: long} = value;
      return `https://www.google.com/maps/place/?q=${lat},${long}&ll=${lat},${long}&z=5`;
    }
    return '';
  }

}
