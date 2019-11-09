import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVps } from 'app/shared/model/vps.model';
import { VpsService } from './vps.service';

@Component({
  selector: 'jhi-vps-delete-dialog',
  templateUrl: './vps-delete-dialog.component.html'
})
export class VpsDeleteDialogComponent {
  vps: IVps;

  constructor(protected vpsService: VpsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vpsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vpsListModification',
        content: 'Deleted an vps'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vps-delete-popup',
  template: ''
})
export class VpsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vps }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VpsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vps = vps;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vps', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vps', { outlets: { popup: null } }]);
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
