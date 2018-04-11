import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { AbstractService } from './AbstractService';

/**
 * This class can request the server to get application version number.
 */
@Injectable()
export class ApplicationService extends AbstractService {

  /**
    * The service that can manage the application configuration.
    *
    * @param http the http server requested to make http requests.
    */
  constructor (http: Http) {
    super(http);
  }

  protected getServiceSpecificUrl(): string {
    return 'application';
  }

  /**
   * Start a GET request and retrieve the version number of the application.
   * This request receive String formatted responses.
   * Request URL is "api/application/version".
   * If an error is raised, then it is forwarded to the view.
   *
   * @return application version number.
   */
  getVersionNumber(): Observable<string | any> {
    const url = `${this.getUrl()}/version`;
    return this.http.get(url)
            .map(res => super.extractStringFromResponse(res))
            .catch(error => super.handleError(error));
  }

}
