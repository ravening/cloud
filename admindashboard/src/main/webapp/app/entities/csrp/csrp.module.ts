import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdmindashboardSharedModule } from 'app/shared/shared.module';
import { CsrpComponent } from './csrp.component';
import { CsrpDetailComponent } from './csrp-detail.component';
import { CsrpUpdateComponent } from './csrp-update.component';
import { CsrpDeletePopupComponent, CsrpDeleteDialogComponent } from './csrp-delete-dialog.component';
import { csrpRoute, csrpPopupRoute } from './csrp.route';

const ENTITY_STATES = [...csrpRoute, ...csrpPopupRoute];

@NgModule({
  imports: [AdmindashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CsrpComponent, CsrpDetailComponent, CsrpUpdateComponent, CsrpDeleteDialogComponent, CsrpDeletePopupComponent],
  entryComponents: [CsrpDeleteDialogComponent]
})
export class AdmindashboardCsrpModule {}
