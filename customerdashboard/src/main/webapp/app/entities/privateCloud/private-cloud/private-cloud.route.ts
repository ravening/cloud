import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';
import { PrivateCloudService } from './private-cloud.service';
import { PrivateCloudComponent } from './private-cloud.component';
import { PrivateCloudDetailComponent } from './private-cloud-detail.component';
import { PrivateCloudUpdateComponent } from './private-cloud-update.component';
import { PrivateCloudDeletePopupComponent } from './private-cloud-delete-dialog.component';
import { IPrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';

@Injectable({ providedIn: 'root' })
export class PrivateCloudResolve implements Resolve<IPrivateCloud> {
  constructor(private service: PrivateCloudService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrivateCloud> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PrivateCloud>) => response.ok),
        map((privateCloud: HttpResponse<PrivateCloud>) => privateCloud.body)
      );
    }
    return of(new PrivateCloud());
  }
}

export const privateCloudRoute: Routes = [
  {
    path: '',
    component: PrivateCloudComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'customerdashboardApp.privateCloudPrivateCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrivateCloudDetailComponent,
    resolve: {
      privateCloud: PrivateCloudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.privateCloudPrivateCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrivateCloudUpdateComponent,
    resolve: {
      privateCloud: PrivateCloudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.privateCloudPrivateCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrivateCloudUpdateComponent,
    resolve: {
      privateCloud: PrivateCloudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.privateCloudPrivateCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const privateCloudPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrivateCloudDeletePopupComponent,
    resolve: {
      privateCloud: PrivateCloudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'customerdashboardApp.privateCloudPrivateCloud.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
