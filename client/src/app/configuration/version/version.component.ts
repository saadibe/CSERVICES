import { Component } from '@angular/core';
import { ApplicationService } from 'app/configuration/services/ApplicationService';

@Component({
  selector: 'version',
  templateUrl: './version.component.html',
  styleUrls: ['./version.component.css']
})
export class VersionComponent {

  private applicationService: ApplicationService;
  public applicationVersion: string;

  constructor(applicationService: ApplicationService) {
    this.applicationService = applicationService;
    this.applicationService.getVersionNumber().subscribe(result => this.applicationVersion = result);
  }

}
