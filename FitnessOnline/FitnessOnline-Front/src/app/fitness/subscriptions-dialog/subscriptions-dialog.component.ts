import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Category } from '../../model/Category';
import { CategoryService } from '../services/category.service';
import { User } from '../../model/User';

@Component({
  selector: 'app-subscriptions-dialog',
  templateUrl: './subscriptions-dialog.component.html',
  styleUrl: './subscriptions-dialog.component.css'
})
export class SubscriptionsDialogComponent implements OnInit {


  subbedCategories: Category[] = [];
  categoriesToUnsub: Category[] = [];
  nonSubbedCategories: Category[] = [];
  categoriesToSub: Category[] = [];
  user!: User;
  selectedCategory: Category | null = null;


  constructor(public matDialogRef: MatDialogRef<SubscriptionsDialogComponent>,
    private categoryService: CategoryService) {
    this.user = JSON.parse(sessionStorage.getItem("user")!);
  }

  ngOnInit() {
    this.categoryService.getSubscribedCategories().subscribe({
      next: (data: any) => {
        this.subbedCategories = data as Category[];
        this.categoryService.getCategories().subscribe({
          next: (data: any) => {
            let idArray: Number[] = this.subbedCategories.map((c) => c.id)
            this.nonSubbedCategories = (data as Category[]).filter(c => !idArray.includes(c.id));
          },
          error: (err: any) => { console.log("Error fetching all categories!") }
        })
      },
      error: (err: any) => { console.log("Error fetching subscribed categories!") }
    });
  }

  unsub(category: Category) {
    this.categoriesToUnsub.push(category);
    this.subbedCategories.splice(this.subbedCategories.indexOf(category), 1);
    if (this.categoriesToSub.includes(category)) {
      this.categoriesToSub.splice(this.categoriesToSub.indexOf(category), 1);
    }
    this.nonSubbedCategories.push(category);
    // this.categoryService.deleteSubscription(category.id).subscribe({
    //   next: (data: any) => {
    //     console.log("successful unsub");
    //     this.categories.splice(this.categories.indexOf(category), 1);
    //   },
    //   error: (err: any) => {
    //     console.log("Error while unsubbing!");
    //   }
    // })
  }

  sub() {
    if (this.selectedCategory !== null) {
      this.subbedCategories.push(this.selectedCategory as Category);
      this.categoriesToSub.push(this.selectedCategory as Category);
      this.nonSubbedCategories.splice(this.nonSubbedCategories.indexOf(this.selectedCategory as Category), 1);
      this.selectedCategory = null;
    }
  }

  save() {
    let obj = {
      toSub: this.categoriesToSub,
      toUnsub: this.categoriesToUnsub
    }
    this.matDialogRef.close(obj);
  }
}
