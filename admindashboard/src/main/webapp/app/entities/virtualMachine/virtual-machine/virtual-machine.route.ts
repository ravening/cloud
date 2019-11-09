import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';
import { VirtualMachineService } from './virtual-machine.service';
import { VirtualMachineComponent } from './virtual-machine.component';
import { VirtualMachineDetailComponent } from './virtual-machine-detail.component';
import { VirtualMachineUpdateComponent } from './virtual-machine-update.component';
import { VirtualMachineDeletePopupComponent } from './virtual-machine-delete-dialog.component';
import { IVirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';

@Injectable({ providedIn: 'root' })
export class VirtualMachineResolve implements Resolve<IVirtualMachine> {
  constructor(private service: VirtualMachineService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVirtualMachine> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VirtualMachine>) => response.ok),
        map((virtualMachine: HttpResponse<VirtualMachine>) => virtualMachine.body)
      );
    }
    return of(new VirtualMachine());
  }
}

export const virtualMachineRoute: Routes = [
  {
    path: '',
    component: VirtualMachineComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'admindashboardApp.virtualMachineVirtualMachine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VirtualMachineDetailComponent,
    resolve: {
      virtualMachine: VirtualMachineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.virtualMachineVirtualMachine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VirtualMachineUpdateComponent,
    resolve: {
      virtualMachine: VirtualMachineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.virtualMachineVirtualMachine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VirtualMachineUpdateComponent,
    resolve: {
      virtualMachine: VirtualMachineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.virtualMachineVirtualMachine.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const virtualMachinePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VirtualMachineDeletePopupComponent,
    resolve: {
      virtualMachine: VirtualMachineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'admindashboardApp.virtualMachineVirtualMachine.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
