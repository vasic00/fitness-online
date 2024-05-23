import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'fitness',
    loadChildren: () =>
      import('./fitness/fitness.module').then((mod) => mod.FitnessModule),
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./account/account.module').then((mod) => mod.AccountModule),
  },
  {
    path: '',
    loadChildren: () =>
      import('./rss/rss.module').then((mod) => mod.RssModule),
  },
  {
    path: '**',
    redirectTo: '/',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
