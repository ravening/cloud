import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderCsrp } from 'app/shared/model/orderService/order-csrp.model';
import { OrderCsrpService } from './order-csrp.service';
import { OrderCsrpComponent } from './order-csrp.component';
import { OrderCsrpDetailComponent } from './order-csrp-detail.component';
import { OrderCsrpUpdateComponent } from './order-csrp-update.component';
import { OrderCsrpDeletePopupComponent } from './order-csrp-delete-dialog.component';
import { IOrderCsrp } from 'app/shared/model/orderService/order-csrp.model';

@Injectable({ providedIn: 'root' })
export class OrderCsrpResolve implements Resolve<IOrderCsrp> {
  constructor(private service: OrderCsrpService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderCsrp> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderCsrp>) => response.ok),
        map((orderCsrp: HttpResponse<OrderCsrp>) => orderCsrp.body)
      );
    }
    return of(new OrderCsrp());
  }
}

export const orderCsrpRoute: Routes = [
  {
    path: '',
    component: OrderCsrpComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'admindashboardApp.orderServiceOrderCsrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderCsrpDetailComponent,
    resolve: {
      orderCsrp: OrderCsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.orderServiceOrderCsrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderCsrpUpdateComponent,
    resolve: {
      orderCsrp: OrderCsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.orderServiceOrderCsrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderCsrpUpdateComponent,
    resolve: {
      orderCsrp: OrderCsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.orderServiceOrderCsrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderCsrpPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderCsrpDeletePopupComponent,
    resolve: {
      orderCsrp: OrderCsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.orderServiceOrderCsrp.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
