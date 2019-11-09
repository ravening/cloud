import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Csrp } from 'app/shared/model/csrp.model';
import { CsrpService } from './csrp.service';
import { CsrpComponent } from './csrp.component';
import { CsrpDetailComponent } from './csrp-detail.component';
import { CsrpUpdateComponent } from './csrp-update.component';
import { CsrpDeletePopupComponent } from './csrp-delete-dialog.component';
import { ICsrp } from 'app/shared/model/csrp.model';

@Injectable({ providedIn: 'root' })
export class CsrpResolve implements Resolve<ICsrp> {
  constructor(private service: CsrpService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICsrp> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Csrp>) => response.ok),
        map((csrp: HttpResponse<Csrp>) => csrp.body)
      );
    }
    return of(new Csrp());
  }
}

export const csrpRoute: Routes = [
  {
    path: '',
    component: CsrpComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.csrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CsrpDetailComponent,
    resolve: {
      csrp: CsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.csrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CsrpUpdateComponent,
    resolve: {
      csrp: CsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.csrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CsrpUpdateComponent,
    resolve: {
      csrp: CsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.csrp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const csrpPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CsrpDeletePopupComponent,
    resolve: {
      csrp: CsrpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.csrp.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
