import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HelloComponent } from './hello/hello.component';
import { VersionComponent } from './version/version.component';
import { ConfigurationHomeComponent } from './home/home.component';

export const appRoutes: Routes = [
  { path: 'configuration',
      component: ConfigurationHomeComponent,
      children: [
        { path: 'hello/:name', component: HelloComponent },
        { path: 'version', component: VersionComponent }
      ]
    }
];


@NgModule({
  imports: [RouterModule.forChild(appRoutes)],
  exports: [RouterModule]
})
export class ConfigurationRoutingModule {}
