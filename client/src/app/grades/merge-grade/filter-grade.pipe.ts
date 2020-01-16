import { Pipe, PipeTransform } from '@angular/core';
import { Grade } from '../../shared/models/grade';

@Pipe({
  name: 'filterGrade'
})
export class FilterGradePipe implements PipeTransform {

  transform(grades: Grade[], query: string): any {
    if ( query ) {
      const re = new RegExp(query, 'i');
      return grades
        .filter(c => re.test(c.name));
    }
    return grades;
  }

}
