import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeListComponent } from './grade-list.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { CountryNameFromIdPipe } from './country-name-from-id.pipe';
import { Grade } from '../../shared/models/grade';
import { Column } from '@ngx-stoui/datatable';

describe('GradeListComponent', () => {
  let component: GradeListComponent;
  let fixture: ComponentFixture<GradeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ GradeListComponent, CountryNameFromIdPipe ],
        schemas: [ NO_ERRORS_SCHEMA ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Table rows', () => {
    let grade: Grade;
    beforeEach(() => {
      grade = {} as Grade;
      grade.id = '1';
      grade.name = 'A Grade';
      grade.cancelled = false;
    });
    it('should get add-grade class if grade has no id', () => {
      grade.id = null;
      expect(component.getRowClass(grade)).toEqual('add-grade');
    });
    it('should get cancelled class if grade is set to cancelled', () => {
      grade.cancelled = true;
      expect(component.getRowClass(grade)).toEqual('cancelled');
    });
    it('should get an empty class if grade has id and is not cancelled', () => {
      expect(component.getRowClass(grade)).toEqual('');
    });
  });

  describe('Input', () => {
    let grade: Grade;
    let col: Column;

    beforeEach(() => {
      grade = {} as Grade;
      grade.id = '1';
      grade.name = 'A Grade';
      grade.cancelled = false;
      col = {} as Column;
      col.prop = 'name';
      col.name = 'Name';
    });

    it('should create an element in component.editing when a value is changed', () => {
      expect(component.editGrade).toBeUndefined();
      component.onValueChange('New Name', grade, col);
      expect(component.editGrade).toBeDefined();
      expect(component.editGrade.id).toEqual(grade.id);
    });
    it('should save when enter is pressed', () => {
      const editGrade = { ...grade };
      component.editGrade = editGrade;
      const spy = spyOn(component.save, 'emit');
      component.onValueChange(null, grade, col, { key: 'Enter' } as any);
      expect(spy).toHaveBeenCalledWith(editGrade);
    });
    it('should emit cancel when escape is pressed and grade has no id', () => {
      const spy = spyOn(component.cancel, 'emit');
      grade.id = null;
      component.editGrade = { ...grade };
      component.onValueChange(null, grade, col, { key: 'Escape' } as any);
      expect(spy).toHaveBeenCalled();
    });
  });

  it('should clear editGrade when saveGrade is called', () => {
    component.editGrade = { id: '1', name: '' } as any;
    component.saveGrade();
    expect(component.editGrade).toBeNull();
  });

  it('should test verifyGrade', () => {
    const grade = { id: '1', name: '', verified: false } as any;
    const spy = spyOn(component.verify, 'emit');
    component.verifyGrade(grade);
    expect(spy.calls.mostRecent().args[ 0 ]).toEqual({ ...grade, verified: true });
  });
});
