import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-account',
        loadChildren: () => import('./user-account/user-account.module').then(m => m.CustomerdashboardUserAccountModule)
      },
      {
        path: 'virtual-machine',
        loadChildren: () =>
          import('./virtualMachine/virtual-machine/virtual-machine.module').then(m => m.VirtualMachineVirtualMachineModule)
      },
      {
        path: 'private-cloud',
        loadChildren: () => import('./privateCloud/private-cloud/private-cloud.module').then(m => m.PrivateCloudPrivateCloudModule)
      },
      {
        path: 'order-vps',
        loadChildren: () => import('./orderService/order-vps/order-vps.module').then(m => m.OrderServiceOrderVpsModule)
      },
      {
        path: 'order-csrp',
        loadChildren: () => import('./orderService/order-csrp/order-csrp.module').then(m => m.OrderServiceOrderCsrpModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerdashboardCustomerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CustomerdashboardEntityModule {}
