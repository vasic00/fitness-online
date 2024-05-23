import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exercises-recommendation',
  templateUrl: './exercises-recommendation.component.html',
  styleUrl: './exercises-recommendation.component.css'
})
export class ExercisesRecommendationComponent implements OnInit {

  muscle: string = "";

  private requestURL: string = "https://api.api-ninjas.com/v1/exercises?muscle=";
  private headerValue: string = "TszWxPpUlS0JxJR8zVndhg==slw7WAF6PRwp5yOc";
  exercises: any[] = [];

  constructor(private httpClient: HttpClient) {

  }
  ngOnInit() {
    this.fetch();
  }

  fetch() {
    const headers = new HttpHeaders().append("X-Api-Key", this.headerValue);
    this.httpClient.get(this.requestURL + this.muscle, { headers }).subscribe({
      next: (data: any) => this.exercises = data as any[],
      error: (err: any) => console.log("Error fetching exercises.")
    })
  }
}
