import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { ProgramInfoComponent } from './program-info/program-info.component';

const routes: Routes = [

  {
    path: '',
    component: HomepageComponent
  },

  {
    path: 'program',
    component: ProgramInfoComponent,
  },

  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FitnessRoutingModule { }
