import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivationRedirectComponent } from './activation-redirect.component';

describe('ActivationRedirectComponent', () => {
  let component: ActivationRedirectComponent;
  let fixture: ComponentFixture<ActivationRedirectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivationRedirectComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ActivationRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
