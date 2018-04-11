import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';

/**
 * This class can request the server to get contact information.
 */
@Injectable()
export class LoginService {

  constructor(private http: Http) {
  }

  authenticate(username: string, password: string): Observable<any> {
    const headers = new Headers();
    if (username != null && password != null) {
    const authorizationValue: string = 'Basic ' + btoa(username + ':' + password);
      headers.append('authorization', authorizationValue);
    }

    return this.http.get('user', {headers: headers})
          .map((res: Response) => res.json())
          .catch( error => this.handleError(error) );
  }

  /**
   * Start a POST request to logout the user.
   * Request URL is `logout`.
   * If an error is raised, then it is forwarded to the view.
   */
  logout(): Observable<string> {
    return this.http.post('logout', {})
            .map(res => this.extractStringFromResponse(res))
            .catch(res => this.handleError(res));
  }

  /**
   * Manage a given response from a request and return the extracted string if found.
   * Return null otherwise.
   *
   * @param res the response to treat.
   * @return the received string.
   */
  protected extractStringFromResponse(res: Response): string {
    if (res.json() != null) {
      return res.json() as string;
    } else {
      return null;
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
