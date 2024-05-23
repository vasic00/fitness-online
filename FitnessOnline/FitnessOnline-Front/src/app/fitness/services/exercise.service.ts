import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Exercise } from '../../model/Exercise';
import { environment } from '../../config/environment';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private httpClient: HttpClient) { }

  public add(exercise: Exercise): any {
    return this.httpClient.post(`${environment.baseURL}${environment.exercisesPath}`, exercise);
  }

  public getAllByUser(): any {
    return this.httpClient.get(`${environment.baseURL}${environment.exercisesPath}`);
  }
}
