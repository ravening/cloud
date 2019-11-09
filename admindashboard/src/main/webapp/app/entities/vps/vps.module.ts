import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdmindashboardSharedModule } from 'app/shared/shared.module';
import { VpsComponent } from './vps.component';
import { VpsDetailComponent } from './vps-detail.component';
import { VpsUpdateComponent } from './vps-update.component';
import { VpsDeletePopupComponent, VpsDeleteDialogComponent } from './vps-delete-dialog.component';
import { vpsRoute, vpsPopupRoute } from './vps.route';

const ENTITY_STATES = [...vpsRoute, ...vpsPopupRoute];

@NgModule({
  imports: [AdmindashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VpsComponent, VpsDetailComponent, VpsUpdateComponent, VpsDeleteDialogComponent, VpsDeletePopupComponent],
  entryComponents: [VpsDeleteDialogComponent]
})
export class AdmindashboardVpsModule {}
