import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerdashboardSharedModule } from 'app/shared/shared.module';
import { PrivateCloudComponent } from './private-cloud.component';
import { PrivateCloudDetailComponent } from './private-cloud-detail.component';
import { PrivateCloudUpdateComponent } from './private-cloud-update.component';
import { PrivateCloudDeletePopupComponent, PrivateCloudDeleteDialogComponent } from './private-cloud-delete-dialog.component';
import { privateCloudRoute, privateCloudPopupRoute } from './private-cloud.route';

const ENTITY_STATES = [...privateCloudRoute, ...privateCloudPopupRoute];

@NgModule({
  imports: [CustomerdashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrivateCloudComponent,
    PrivateCloudDetailComponent,
    PrivateCloudUpdateComponent,
    PrivateCloudDeleteDialogComponent,
    PrivateCloudDeletePopupComponent
  ],
  entryComponents: [PrivateCloudDeleteDialogComponent]
})
export class PrivateCloudPrivateCloudModule {}
