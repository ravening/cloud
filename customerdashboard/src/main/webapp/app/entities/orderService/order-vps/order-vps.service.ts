import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderVps } from 'app/shared/model/orderService/order-vps.model';

type EntityResponseType = HttpResponse<IOrderVps>;
type EntityArrayResponseType = HttpResponse<IOrderVps[]>;

@Injectable({ providedIn: 'root' })
export class OrderVpsService {
  public resourceUrl = SERVER_API_URL + 'services/orderservice/api/order-vps';
  constructor(protected http: HttpClient) {}

  create(orderVps: IOrderVps): Observable<EntityResponseType> {
    console.log('sending a post request');
    return this.http.post<IOrderVps>(this.resourceUrl, orderVps, { observe: 'response' });
  }

  update(orderVps: IOrderVps): Observable<EntityResponseType> {
    return this.http.put<IOrderVps>(this.resourceUrl, orderVps, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderVps>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderVps[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  orderVps() {
    console.log('sending the get request');
    return this.http.get(this.resourceUrl);
  }
}
