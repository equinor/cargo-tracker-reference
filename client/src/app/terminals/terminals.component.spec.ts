import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminalsComponent } from './terminals.component';
import { Store, StoreModule } from '@ngrx/store';

describe('TerminalsComponent', () => {
  let component: TerminalsComponent;
  let fixture: ComponentFixture<TerminalsComponent>;
  let store: Store<any>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [ StoreModule.forRoot({}) ],
      declarations: [ TerminalsComponent ]
    });

    await TestBed.compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalsComponent);
    component = fixture.componentInstance;
    store = TestBed.get<Store<any>>(Store);

    spyOn(store, 'dispatch').and.callThrough();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
