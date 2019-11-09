import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdmindashboardSharedModule } from 'app/shared/shared.module';
import { OrderVpsComponent } from './order-vps.component';
import { OrderVpsDetailComponent } from './order-vps-detail.component';
import { OrderVpsUpdateComponent } from './order-vps-update.component';
import { OrderVpsDeletePopupComponent, OrderVpsDeleteDialogComponent } from './order-vps-delete-dialog.component';
import { orderVpsRoute, orderVpsPopupRoute } from './order-vps.route';

const ENTITY_STATES = [...orderVpsRoute, ...orderVpsPopupRoute];

@NgModule({
  imports: [AdmindashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderVpsComponent,
    OrderVpsDetailComponent,
    OrderVpsUpdateComponent,
    OrderVpsDeleteDialogComponent,
    OrderVpsDeletePopupComponent
  ],
  entryComponents: [OrderVpsDeleteDialogComponent]
})
export class OrderServiceOrderVpsModule {}
