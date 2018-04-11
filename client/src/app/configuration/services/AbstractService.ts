import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs/Observable';

/**
 * This class can request the server to get information about applications from BEL Database.
 */
export abstract class AbstractService {

  protected http: Http;
  protected urlPrefix: string = 'api/';

  constructor(http: Http) {
    this.http = http;
  }

  /**
   * Return the specific url for the current service.
   * This will be used in the getUrl() method and will be concatened with the url prefix.
   *
   * @return the specific url for the current service.
   */
  protected abstract getServiceSpecificUrl(): string;

  /**
   * Return the url for the current service.
   * Every service URL is composed of the prefix 'api' and an specific path.
   *
   * @return the complete url for the service.
   */
  public getUrl(): string {
    return this.urlPrefix + this.getServiceSpecificUrl();
  }


  /**
   * Manage a given response from a request and return the extracted id if found.
   * Return null otherwise.
   *
   * @param res the response to treat.
   * @return the received id.
   */
  protected extractIdFromResponse(res: Response): number {
    this.manageErrorCodes(res.status, res);

    if (res.json() != null) {
      return res.json() as number;
    } else {
      return null;
    }
  }

  /**
   * Manage a given response from a request and return the extracted string if found.
   * Return null otherwise.
   *
   * @param res the response to treat.
   * @return the received string.
   */
  protected extractStringFromResponse(res: Response): string {
    this.manageErrorCodes(res.status, res);

    if (res.text() != null) {
      return res.text() as string;
    } else {
      return null;
    }
  }

  /**
   * Manage the status code from a response.
   * If the response contains a 203, 204 or 404 code, then an error is thrown
   * to warn the view.
   *
   * @param res the response to treat.
   */
  protected manageErrorCodes(status: number, res: Response): void {
    if (status == 203) {
      throw new Error('Non autorisé : ' + res.text());
    } else if (status == 404) {
      throw new Error('Non trouvé : ' + res.text());
    } else if (status == 403) {
      throw new Error('Interdit : ' + res.text());
    } else if (status == 503) {
      throw new Error('Erreur de connection : ' + res.text());
    }
  }

  /**
   * Communicate the found error to the view through an Observable Exception.
   *
   * @param res the response to treat.
   */
   protected handleError(error: Response | any): any {
     let errMsg: string;
     if (error instanceof Response) {
       try{
          errMsg = `${error.status} - ${error.json().message || ''}`;
       }catch(e){
          errMsg = `${error.status} - ${error.text() || ''}`;
       }
     } else {
       errMsg = error.message ? error.message : error.toString();
     }

     return Observable.throw(errMsg);
   }

}
