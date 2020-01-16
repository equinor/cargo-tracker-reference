import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminalFilterComponent } from './terminal-filter.component';

describe('TerminalFilterComponent', () => {
  let component: TerminalFilterComponent;
  let fixture: ComponentFixture<TerminalFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ TerminalFilterComponent ]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
