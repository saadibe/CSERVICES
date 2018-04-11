import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs/Observable';

/**
 * This class can request the server and convert data.
 * @param <T> the type of entity to manage.
 */
export abstract class AbstractService<T> {

  protected http: Http;
  protected urlPrefix: string = 'api/';

  constructor(http: Http) {
    this.http = http;
  }

  /**
    * Convert an input to a constructed object.
    */
  protected abstract convertToEntity(input: any): T;

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
   * Start a GET request and retrieve the list of all entities.
   * This request receive String formatted responses.
   * Request URL is "${this.getUrl()}/all".
   * If an error is raised, then it is forwarded to the view.
   *
   * @return the list of all entities.
   */
  getAll(): Observable<T[] | any> {
    const url = `${this.getUrl()}/all`;
    return this.http.get(url)
            .map(res => this.extractListFromResponse(res))
            .catch(error => this.handleError(error));
  }

  /**
   * Start a GET request and retrieve the requested entity.
   * This request receive String formatted responses.
   * Request URL is "${this.getUrl()}/${id}".
   * If an error is raised, then it is forwarded to the view.
   *
   * @return the entity.
   */
  get(id: number): Observable<T | any> {
    const url = `${this.getUrl()}/${id}`;
    return this.http.get(url)
            .map(res => this.extractSingleEntityFromResponse(res))
            .catch(error => this.handleError(error));
  }

  /**
   * Start a GET request and delete the entity with given id.
   * This request receive Json formatted response and convert them into entities.
   * Request URL is `${this.getUrl()}/remove/${id}`.
   * If an error is raised, then it is forwarded to the view.
   *
   * @param id the id of the entity.
   * @return the id of deleted entity.
   */
  delete(id: number): Observable<number | any> {
    const url = `${this.getUrl()}/remove/${id}`;
    return this.http.get(url)
            .map(res => this.extractIdFromResponse(res))
            .catch(res => this.handleError(res));
  }

  /**
   * Start a POST request to create the given entity in body.
   * This request receive Json formatted response and convert them into entities.
   * Request URL is `${this.getUrl()}/create`. Parameters are passed in body.
   * If an error is raised, then it is forwarded to the view.
   *
   * @param entity the entity to create.
   * @return the create entity.
   */
  create(entity: T): Observable<T | any> {
    const url = `${this.getUrl()}/create`;

    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });

    return this.http.post(url, entity, options)
            .map(res => this.extractSingleEntityFromResponse(res))
            .catch(res => this.handleError(res));
  }

  /**
   * Start a POST request to update the given entity in body.
   * This request receive Json formatted response and convert them into entities.
   * Request URL is `${this.getUrl()}/update`. Parameters are passed in body.
   * If an error is raised, then it is forwarded to the view.
   *
   * @param entity the entity to update.
   * @return the updated entity.
   */
  update(entity: T): Observable<T | any> {
    const url = `${this.getUrl()}/edit`;

    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });
    return this.http.post(url, entity, options)
            .map(res => this.extractSingleEntityFromResponse(res))
            .catch(res => this.handleError(res));
  }


  /**
   * Manage a given response from a request and return the found entity from body.
   * Throw an error if no entity was found in body.
   *
   * @param res the response to treat.
   * @return the received entity.
   */
  protected extractSingleEntityFromResponse(res: Response): T {
    if(this.manageErrorCodes(res.status, res) && res.text != null && res.text().length > 0 && res.json() != null) {
      return this.convertToEntity(res.json());
    } else {
      return null;
    }
  }

  /**
   * Manage a given response from a request and return the found list of entities from body.
   * Return an empty array if the body is empty.
   *
   * @param res the response to treat.
   * @return the received list of entities.
   */
  protected extractListFromResponse(res: Response): T[] {
    if (this.manageErrorCodes(res.status, res) && res.json() != null) {
      const currentJsonString: Object[] = res.json();
      return currentJsonString.map(jsonObject => {return this.convertToEntity(jsonObject); });
    } else {
      return new Array<T>();
    }
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
   * Manage the status code from a response.
   * If the response contains a 203, 204 or 404 code, then an error is thrown
   * to warn the view.
   *
   * @param res the response to treat.
   */
  protected manageErrorCodes(status: number, res: Response): boolean {
    if (status == 203) {
      throw new Error('Non autorisé : ' + res.text());
    } else if (status == 404) {
      throw new Error('Non trouvé : ' + res.text());
    } else if (status == 403) {
      throw new Error('Interdit : ' + res.text());
    } else if (status == 503) {
      throw new Error('Erreur de connection : ' + res.text());
    }

    return true;
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
