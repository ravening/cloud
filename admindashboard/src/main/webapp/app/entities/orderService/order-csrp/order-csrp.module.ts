import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdmindashboardSharedModule } from 'app/shared/shared.module';
import { OrderCsrpComponent } from './order-csrp.component';
import { OrderCsrpDetailComponent } from './order-csrp-detail.component';
import { OrderCsrpUpdateComponent } from './order-csrp-update.component';
import { OrderCsrpDeletePopupComponent, OrderCsrpDeleteDialogComponent } from './order-csrp-delete-dialog.component';
import { orderCsrpRoute, orderCsrpPopupRoute } from './order-csrp.route';

const ENTITY_STATES = [...orderCsrpRoute, ...orderCsrpPopupRoute];

@NgModule({
  imports: [AdmindashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderCsrpComponent,
    OrderCsrpDetailComponent,
    OrderCsrpUpdateComponent,
    OrderCsrpDeleteDialogComponent,
    OrderCsrpDeletePopupComponent
  ],
  entryComponents: [OrderCsrpDeleteDialogComponent]
})
export class OrderServiceOrderCsrpModule {}
