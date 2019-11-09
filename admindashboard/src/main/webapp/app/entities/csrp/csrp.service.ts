import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICsrp } from 'app/shared/model/csrp.model';

type EntityResponseType = HttpResponse<ICsrp>;
type EntityArrayResponseType = HttpResponse<ICsrp[]>;

@Injectable({ providedIn: 'root' })
export class CsrpService {
  public resourceUrl = SERVER_API_URL + 'api/csrps';

  constructor(protected http: HttpClient) {}

  create(csrp: ICsrp): Observable<EntityResponseType> {
    return this.http.post<ICsrp>(this.resourceUrl, csrp, { observe: 'response' });
  }

  update(csrp: ICsrp): Observable<EntityResponseType> {
    return this.http.put<ICsrp>(this.resourceUrl, csrp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICsrp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICsrp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
