import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionsDialogComponent } from './subscriptions-dialog.component';

describe('SubscriptionsDialogComponent', () => {
  let component: SubscriptionsDialogComponent;
  let fixture: ComponentFixture<SubscriptionsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubscriptionsDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubscriptionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
