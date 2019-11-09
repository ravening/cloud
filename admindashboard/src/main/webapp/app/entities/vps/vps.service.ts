import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVps } from 'app/shared/model/vps.model';

type EntityResponseType = HttpResponse<IVps>;
type EntityArrayResponseType = HttpResponse<IVps[]>;

@Injectable({ providedIn: 'root' })
export class VpsService {
  public resourceUrl = SERVER_API_URL + 'api/vps';

  constructor(protected http: HttpClient) {}

  create(vps: IVps): Observable<EntityResponseType> {
    return this.http.post<IVps>(this.resourceUrl, vps, { observe: 'response' });
  }

  update(vps: IVps): Observable<EntityResponseType> {
    return this.http.put<IVps>(this.resourceUrl, vps, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVps>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVps[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
