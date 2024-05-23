import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ProgramService } from '../services/program.service';
import { Program } from '../../model/Program';
import { Category } from '../../model/Category';
import { CategoryService } from '../services/category.service';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrl: './browse.component.css',
})

export class BrowseComponent implements OnInit {

  constructor(private programService: ProgramService,
    private categoryService: CategoryService) { }

  @ViewChild('paginator') paginator!: MatPaginator;
  @Input('profile') profile: boolean = false;

  programs: Array<Program> = [];
  categories: Array<Category> = [];
  selectedTypeOfProgram: string | null = null;
  typesOfPrograms: string[] = ["All programs", "Only attendable"];
  selectedCategory: Category | null = null;
  priceFrom: number | null = null;
  priceTo: number | null = null;
  nameContains: string | null = null;
  selectedLevel: number | null = null;
  participationIds: number[] = [];

  length = 500;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [2, 4, 10, 20, 40, 60, 80];
  showFirstLastButtons = true;

  ngOnInit() {
    if (this.profile)
      this.typesOfPrograms = ["All programs", "Available", "Expired"];
    this.categoryService.getCategories().subscribe({
      next: (data: any) => {
        this.categories = data;
        let allCategories: Category = new Category();
        allCategories.name = "All categories";
        this.categories.unshift(allCategories);
        let obj: any = null;
        if (this.profile && sessionStorage.getItem("profileBrowseData") !== null)
          obj = JSON.parse(sessionStorage.getItem("profileBrowseData")!);
        else if (sessionStorage.getItem("browseData") !== null)
          obj = JSON.parse(sessionStorage.getItem("browseData")!);
        if (obj !== null) {
          this.categories.forEach((cat: Category) => {
            if (obj.selectedCategory !== null && cat.id === obj.selectedCategory.id)
              this.selectedCategory = cat;
          })
          this.selectedTypeOfProgram = obj.selectedTypeOfProgram;
          this.priceFrom = obj.priceFrom;
          this.priceTo = obj.priceTo;
          this.nameContains = obj.nameContains;
          this.selectedLevel = obj.selectedLevel;
          this.pageIndex = obj.pageIndex;
          this.pageSize = obj.pageSize;
          this.length = obj.length
          this.fetch();
        }
        else this.fetch();
      },
      error: (err: any) => {
        console.log("error u dohvatanju pocetnih kategorija");
      }
    });

    // if (this.profile) {
    //   if (sessionStorage.getItem("profileBrowseData") !== null) {
    //     this.programService.getProgramsForCreator(this.pageIndex, this.pageSize).subscribe({
    //       next: (data: any) => {
    //         this.programs = data.programs;
    //       },
    //       error: (err: any) => {
    //         console.log("error u dohvatanju pocetnih programa");
    //       }
    //     });
    //   }
    // }
    // else {
    //   if (sessionStorage.getItem("browseData") !== null) {
    //     this.programService.getPrograms(this.pageIndex, this.pageSize).subscribe({
    //       next: (data: any) => {
    //         this.programs = data.programs;
    //       },
    //       error: (err: any) => {
    //         console.log("error u dohvatanju pocetnih programa");
    //       }
    //     });
    //   }
    // }
    if (sessionStorage.getItem("user") !== null) {
      this.programService.getParticipations().subscribe((data: Array<number>) => this.participationIds = data);
    }
  }

  fetch() {

    let valid: string = '';
    if (this.profile) {
      if (this.selectedTypeOfProgram === null || this.selectedTypeOfProgram === "All programs")
        valid = "-";
      else if (this.selectedTypeOfProgram === "Available")
        valid = "true";
      else if (this.selectedTypeOfProgram === "Expired")
        valid = "false";
      console.log("VALID JE: " + valid);
      console.log("SELECTED PROGRAM JE: " + this.selectedTypeOfProgram);
    }
    else {
      if (this.selectedTypeOfProgram === null || this.selectedTypeOfProgram === "All programs")
        valid = "false";
      else valid = "true";
    }

    let categoryName: string = '-';
    if (this.selectedCategory !== null && this.selectedCategory.name !== "All categories")
      categoryName = this.selectedCategory.name;

    let priceStart: number = -1;
    if (this.priceFrom !== null)
      priceStart = this.priceFrom;

    let priceEnd: number = -1;
    if (this.priceTo !== null)
      priceEnd = this.priceTo;

    let nameOfProgram: string = '-';
    if (this.nameContains !== undefined && this.nameContains !== null)
      nameOfProgram = this.nameContains.trim();
    if (nameOfProgram === '')
      nameOfProgram = '-';

    console.log("Ime programa " + nameOfProgram);
    console.log("Name contains: " + this.nameContains);

    let programDifficulty: number = -1;
    if (this.selectedLevel !== null)
      programDifficulty = this.selectedLevel;

    if (this.profile) {
      this.programService.getFilteredProgramsForCreator(priceStart, priceEnd, valid, nameOfProgram, categoryName, programDifficulty, this.pageIndex, this.pageSize).subscribe({
        next: (data: any) => {
          this.programs = data.programs;
          this.saveCurrentFilterValues();
        },
        error: (err: any) => {
          console.log("Greska u getFilteredProgramsForCreator");
        }
      })
    }
    else {
      this.programService.getFilteredPrograms(priceStart, priceEnd, valid, nameOfProgram, categoryName, programDifficulty, this.pageIndex, this.pageSize).subscribe({
        next: (data: any) => {
          console.log(data.programs);
          this.programs = data.programs;
          // console.log("selektovana kategorija: " + this.selectedCategory!.name);
          this.saveCurrentFilterValues();
        },
        error: (err: any) => {
          console.log("Greska u getFilteredPrograms");
        }
      })
    }
  }

  handlePageEvent(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.length = event.length;
    this.pageIndex = event.pageIndex;
    this.fetch();
  }

  filter() {
    console.log("inside filter");
    this.pageIndex = 0;
    this.fetch();
    this.paginator.firstPage();
  }

  private saveCurrentFilterValues() {
    let obj = {
      selectedTypeOfProgram: this.selectedTypeOfProgram,
      selectedCategory: this.selectedCategory,
      priceFrom: this.priceFrom,
      priceTo: this.priceTo,
      nameContains: this.nameContains,
      selectedLevel: this.selectedLevel,
      pageIndex: this.pageIndex,
      pageSize: this.pageSize,
      length: this.length
    }
    if (this.profile)
      sessionStorage.setItem("profileBrowseData", JSON.stringify(obj));
    else sessionStorage.setItem("browseData", JSON.stringify(obj));
  }
}
