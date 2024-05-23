import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RssComponent } from './rss.component';

const routes: Routes = [
  {
    path: "",
    component: RssComponent
  },
  {
    path: '**',
    redirectTo: '/',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RssRoutingModule { }
