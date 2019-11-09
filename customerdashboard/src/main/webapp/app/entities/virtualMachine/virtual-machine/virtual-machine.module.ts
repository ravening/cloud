import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerdashboardSharedModule } from 'app/shared/shared.module';
import { VirtualMachineComponent } from './virtual-machine.component';
import { VirtualMachineDetailComponent } from './virtual-machine-detail.component';
import { VirtualMachineUpdateComponent } from './virtual-machine-update.component';
import { VirtualMachineDeletePopupComponent, VirtualMachineDeleteDialogComponent } from './virtual-machine-delete-dialog.component';
import { virtualMachineRoute, virtualMachinePopupRoute } from './virtual-machine.route';

const ENTITY_STATES = [...virtualMachineRoute, ...virtualMachinePopupRoute];

@NgModule({
  imports: [CustomerdashboardSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VirtualMachineComponent,
    VirtualMachineDetailComponent,
    VirtualMachineUpdateComponent,
    VirtualMachineDeleteDialogComponent,
    VirtualMachineDeletePopupComponent
  ],
  entryComponents: [VirtualMachineDeleteDialogComponent]
})
export class VirtualMachineVirtualMachineModule {}
