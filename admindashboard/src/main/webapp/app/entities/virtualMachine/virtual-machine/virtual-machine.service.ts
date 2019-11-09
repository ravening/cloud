import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';

type EntityResponseType = HttpResponse<IVirtualMachine>;
type EntityArrayResponseType = HttpResponse<IVirtualMachine[]>;

@Injectable({ providedIn: 'root' })
export class VirtualMachineService {
  public resourceUrl = SERVER_API_URL + 'services/virtualmachine/api/virtual-machines';

  constructor(protected http: HttpClient) {}

  create(virtualMachine: IVirtualMachine): Observable<EntityResponseType> {
    return this.http.post<IVirtualMachine>(this.resourceUrl, virtualMachine, { observe: 'response' });
  }

  update(virtualMachine: IVirtualMachine): Observable<EntityResponseType> {
    return this.http.put<IVirtualMachine>(this.resourceUrl, virtualMachine, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVirtualMachine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVirtualMachine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
