import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';
import { VirtualMachineService } from './virtual-machine.service';

@Component({
  selector: 'jhi-virtual-machine-delete-dialog',
  templateUrl: './virtual-machine-delete-dialog.component.html'
})
export class VirtualMachineDeleteDialogComponent {
  virtualMachine: IVirtualMachine;

  constructor(
    protected virtualMachineService: VirtualMachineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.virtualMachineService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'virtualMachineListModification',
        content: 'Deleted an virtualMachine'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-virtual-machine-delete-popup',
  template: ''
})
export class VirtualMachineDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ virtualMachine }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VirtualMachineDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.virtualMachine = virtualMachine;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/virtual-machine', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/virtual-machine', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
