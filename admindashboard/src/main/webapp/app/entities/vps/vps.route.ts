import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vps } from 'app/shared/model/vps.model';
import { VpsService } from './vps.service';
import { VpsComponent } from './vps.component';
import { VpsDetailComponent } from './vps-detail.component';
import { VpsUpdateComponent } from './vps-update.component';
import { VpsDeletePopupComponent } from './vps-delete-dialog.component';
import { IVps } from 'app/shared/model/vps.model';

@Injectable({ providedIn: 'root' })
export class VpsResolve implements Resolve<IVps> {
  constructor(private service: VpsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVps> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vps>) => response.ok),
        map((vps: HttpResponse<Vps>) => vps.body)
      );
    }
    return of(new Vps());
  }
}

export const vpsRoute: Routes = [
  {
    path: '',
    component: VpsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.vps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VpsDetailComponent,
    resolve: {
      vps: VpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.vps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VpsUpdateComponent,
    resolve: {
      vps: VpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.vps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VpsUpdateComponent,
    resolve: {
      vps: VpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.vps.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vpsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VpsDeletePopupComponent,
    resolve: {
      vps: VpsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.vps.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
