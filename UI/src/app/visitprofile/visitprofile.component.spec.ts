import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitprofileComponent } from './visitprofile.component';

describe('VisitprofileComponent', () => {
  let component: VisitprofileComponent;
  let fixture: ComponentFixture<VisitprofileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisitprofileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
