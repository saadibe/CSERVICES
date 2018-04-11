import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DemoHomeComponent } from './home/home.component';

import { AuthenticationGuard } from '../AuthenticationGuard';

export const appRoutes: Routes = [
  { path: 'demo', component: DemoHomeComponent, canActivate: [AuthenticationGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(appRoutes)],
  exports: [RouterModule],
  providers: [ AuthenticationGuard ]
})
export class DemoRoutingModule {}
