import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Exercise } from '../../model/Exercise';

@Component({
  selector: 'app-exercise-dialog',
  templateUrl: './exercise-dialog.component.html',
  styleUrl: './exercise-dialog.component.css'
})
export class ExerciseDialogComponent implements OnInit {

  form!: FormGroup;
  constructor(private dialogRef: MatDialogRef<ExerciseDialogComponent>,
    private formBuilder: FormBuilder) {

  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      intensity: ['', [Validators.required, Validators.min]],
      result: ['', [Validators.required, Validators.min]],
      duration: ['', [Validators.required, Validators.min]],
    })
  }

  onSubmit() {
    if (this.form.valid) {
      let exercise: Exercise = new Exercise();
      exercise.name = this.form.get("name")?.value;
      exercise.intensity = this.form.get("intensity")?.value;
      exercise.duration = this.form.get("duration")?.value;
      exercise.result = this.form.get("result")?.value;
      this.dialogRef.close(exercise);
    }
  }

}
