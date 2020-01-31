import { NameFromIdPipe } from './name-from-id.pipe';

describe('NameFromIdPipe', () => {
  it('create an instance', () => {
    const pipe = new NameFromIdPipe();
    expect(pipe).toBeTruthy();
  });
  it('should return the name from a VersionedEntityList', () => {
    const pipe = new NameFromIdPipe();
    const obj = [ { id: '1', name: 'Name' } ];
    const name = pipe.transform('1', obj as any);
    expect(name).toEqual('Name');
  });
});
