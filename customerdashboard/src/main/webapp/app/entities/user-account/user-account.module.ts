import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerdashboardSharedModule } from 'app/shared/shared.module';
import { UserAccountComponent } from './user-account.component';
import { UserAccountDetailComponent } from './user-account-detail.component';
import { UserAccountUpdateComponent } from './user-account-update.component';
import { UserAccountDeletePopupComponent, UserAccountDeleteDialogComponent } from './user-account-delete-dialog.component';
import { userAccountRoute, userAccountPopupRoute } from './user-account.route';

const ENTITY_STATES = [...userAccountRoute, ...userAccountPopupRoute];

@NgModule({
  imports: [CustomerdashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserAccountComponent,
    UserAccountDetailComponent,
    UserAccountUpdateComponent,
    UserAccountDeleteDialogComponent,
    UserAccountDeletePopupComponent
  ],
  entryComponents: [UserAccountDeleteDialogComponent]
})
export class CustomerdashboardUserAccountModule {}
