import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../model/Category';
import { SpecificAttribute } from '../../model/SpecificAttribute';
import { CategoryService } from '../services/category.service';
import { Program } from '../../model/Program';
import { CreatorInfo } from '../../model/CreatorInfo';
import { User } from '../../model/User';
import { Attribute } from '../../model/Attribute';
import { ProgramService } from '../services/program.service';
import { Observable, forkJoin } from 'rxjs';
import { FeedbackService } from '../services/feedback.service';

interface ImagePreview {
  url: string | ArrayBuffer | null;
  file: File;
}

@Component({
  selector: 'app-add-program',
  templateUrl: './add-program.component.html',
  styleUrl: './add-program.component.css',
})
export class AddProgramComponent implements OnInit {

  // imageUrl: string | ArrayBuffer | null = null;
  @Output('refresh')
  refresh: EventEmitter<string> = new EventEmitter();
  images: ImagePreview[] = [];
  addProgramForm!: FormGroup;
  categories: Array<Category> = [];
  specificAttributes: Array<SpecificAttribute> = [];
  private uniqueStrLen: number = 16;

  constructor(private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private programService: ProgramService,
    private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    this.addProgramForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min]],
      selectControl: ['', Validators.required],
      ending: ['', Validators.required],
      contact: ['', Validators.required],
      instructor: ['', Validators.required],
      description: [''],
      location: ['', Validators.required],
      difficultyLevel: ['', Validators.required],
    });
    this.categoryService.getCategories().subscribe({
      next: (data: any) => {
        this.categories = data as Array<Category>;
      },
      error: (err: any) => {
        console.log("greska pri dohvatanju kategorija");
      }
    })
  }

  onSubmit() {
    if (this.addProgramForm.valid) {
      let program: Program = new Program();
      program.category = this.addProgramForm.value.selectControl.name;
      program.name = this.addProgramForm.value.name;
      program.description = this.addProgramForm.value.description;
      program.contact = this.addProgramForm.value.contact;
      program.ending = this.addProgramForm.value.ending;
      program.difficultyLevel = this.addProgramForm.value.difficultyLevel;
      program.price = this.addProgramForm.value.price;
      program.instructorInfo = this.addProgramForm.value.instructor;
      program.location = this.addProgramForm.value.location;
      let creator: CreatorInfo = new CreatorInfo();
      let user: User = JSON.parse(sessionStorage.getItem("user")!) as User;
      creator.id = user.id;
      creator.info = user.firstname + " " + user.lastname;
      program.creator = creator;
      this.specificAttributes.forEach((sa: SpecificAttribute) => {
        console.log(sa);
        let a: Attribute = new Attribute();
        a.specificAttributeId = sa.id;
        // let saName: string = sa.name;
        a.value = this.addProgramForm.get(sa.name)?.value;
        console.log(a.value);
        program.attributes.push(a)
      });
      let uniqueStr: string = this.generateRandomString(this.uniqueStrLen);
      if (this.images.length !== 0) {
        forkJoin(this.getImageObservables(uniqueStr)).subscribe({
          next: (data: any) => {
            console.log(data);
            this.subscribeToProgram(program, uniqueStr);
          },
          error: (err: any) => {
            console.log("An error occurred while uploading images.");
          }
        });
      }
      else {
        this.subscribeToProgram(program, uniqueStr);
      }
    }
    // else {
    //   Object.values(this.addProgramForm.controls).forEach((control) => {
    //     if (control.invalid) {
    //       control.markAsDirty();
    //       control.updateValueAndValidity({ onlySelf: true });
    //     }
    //   });
    // }
  }

  submitForm() {
    this.onSubmit();
  }

  onSelectionChange(event: any) {
    if (this.specificAttributes != null && this.specificAttributes != undefined) {
      this.specificAttributes.forEach(sa => {
        this.addProgramForm.removeControl(sa.name);
      });
    }

    console.log(event);
    this.specificAttributes = (event.value as Category).specificAttributes;

    if (this.specificAttributes != null && this.specificAttributes != undefined) {
      this.specificAttributes.forEach(sa => {
        this.addFormControl(sa);
      });
    }
  }

  addFormControl(sa: SpecificAttribute) {
    this.addProgramForm.addControl(sa.name, this.formBuilder.control('', Validators.required));
  }

  onFileSelected(event: any) {
    const files = event.target.files as FileList;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      this.previewImage(file);
    }
  }

  previewImage(file: File) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.images.push({ url: reader.result, file });
    };
  }

  removeImage(image: ImagePreview) {
    const index = this.images.indexOf(image);
    if (index !== -1) {
      this.images.splice(index, 1);
    }
  }

  generateRandomString(length: number): string {
    const timestamp = (new Date()).getTime().toString(36);
    const randomString = Math.random().toString(36).substr(2, length - timestamp.length);
    return timestamp + randomString;
  }

  getImageObservables(uniqueStr: string): Observable<any>[] {
    let array: Observable<any>[] = [];
    this.images.forEach((image: ImagePreview) => {
      console.log(image);
      array.push(this.programService.uploadImageForProgram(image.file, uniqueStr));
    });
    return array;
  }

  subscribeToProgram(program: Program, uniqueStr: string) {
    this.programService.addProgram(program, uniqueStr).subscribe({
      next: (data: any) => {
        this.feedbackService.showMessage("Program successfully created!")
        this.refresh.emit("refresh");
        this.addProgramForm.reset();
        Object.values(this.addProgramForm.controls).forEach(control => {
          control.markAsPristine();
          control.markAsUntouched();
          control.setErrors(null);
        });
        this.images = [];
      },
      error: (err: any) => {
        console.log("An error occurred while creating new program.")
      }
    })
  }

  // uploadImages() {
  //   console.log('Files to be uploaded:', this.images.map(image => image.file));
  // }

}
