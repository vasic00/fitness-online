import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExercisesRecommendationComponent } from './exercises-recommendation.component';

describe('ExercisesRecommendationComponent', () => {
  let component: ExercisesRecommendationComponent;
  let fixture: ComponentFixture<ExercisesRecommendationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExercisesRecommendationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExercisesRecommendationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
