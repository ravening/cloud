import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderCsrp } from 'app/shared/model/orderService/order-csrp.model';

type EntityResponseType = HttpResponse<IOrderCsrp>;
type EntityArrayResponseType = HttpResponse<IOrderCsrp[]>;

@Injectable({ providedIn: 'root' })
export class OrderCsrpService {
  public resourceUrl = SERVER_API_URL + 'services/orderservice/api/order-csrps';

  constructor(protected http: HttpClient) {}

  create(orderCsrp: IOrderCsrp): Observable<EntityResponseType> {
    return this.http.post<IOrderCsrp>(this.resourceUrl, orderCsrp, { observe: 'response' });
  }

  update(orderCsrp: IOrderCsrp): Observable<EntityResponseType> {
    return this.http.put<IOrderCsrp>(this.resourceUrl, orderCsrp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderCsrp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderCsrp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
