import { CountryNameFromIdPipe } from './country-name-from-id.pipe';

describe('CountryNameFromIdPipe', () => {
  it('create an instance', () => {
    const pipe = new CountryNameFromIdPipe();
    expect(pipe).toBeTruthy();
  });
  it('should return the name of a country when an id and country list is passed in', () => {
    const pipe = new CountryNameFromIdPipe();
    const countries = [{name: 'A Country', id: '1'}] as any;
    const id = '1';
    expect(pipe.transform(id, countries)).toEqual(countries[0].name);
  });
});
