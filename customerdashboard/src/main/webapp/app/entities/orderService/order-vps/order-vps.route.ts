import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderVps } from 'app/shared/model/orderService/order-vps.model';
import { OrderVpsService } from './order-vps.service';
import { OrderVpsComponent } from './order-vps.component';
import { OrderVpsDetailComponent } from './order-vps-detail.component';
import { OrderVpsUpdateComponent } from './order-vps-update.component';
import { OrderVpsDeletePopupComponent } from './order-vps-delete-dialog.component';
import { IOrderVps } from 'app/shared/model/orderService/order-vps.model';

@Injectable({ providedIn: 'root' })
export class OrderVpsResolve implements Resolve<IOrderVps> {
  constructor(private service: OrderVpsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderVps> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderVps>) => response.ok),
        map((orderVps: HttpResponse<OrderVps>) => orderVps.body)
      );
    }
    return of(new OrderVps());
  }
}

export const orderVpsRoute: Routes = [
  {
    path: '',
    component: OrderVpsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'customerdashboardApp.orderServiceOrderVps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderVpsDetailComponent,
    resolve: {
      orderVps: OrderVpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.orderServiceOrderVps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderVpsUpdateComponent,
    resolve: {
      orderVps: OrderVpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.orderServiceOrderVps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderVpsUpdateComponent,
    resolve: {
      orderVps: OrderVpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.orderServiceOrderVps.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderVpsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderVpsDeletePopupComponent,
    resolve: {
      orderVps: OrderVpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.orderServiceOrderVps.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
