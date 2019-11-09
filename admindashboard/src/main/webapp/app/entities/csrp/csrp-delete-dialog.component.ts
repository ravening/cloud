import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICsrp } from 'app/shared/model/csrp.model';
import { CsrpService } from './csrp.service';

@Component({
  selector: 'jhi-csrp-delete-dialog',
  templateUrl: './csrp-delete-dialog.component.html'
})
export class CsrpDeleteDialogComponent {
  csrp: ICsrp;

  constructor(protected csrpService: CsrpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.csrpService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'csrpListModification',
        content: 'Deleted an csrp'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-csrp-delete-popup',
  template: ''
})
export class CsrpDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csrp }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CsrpDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.csrp = csrp;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/csrp', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/csrp', { outlets: { popup: null } }]);
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
