import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';
import { PrivateCloudService } from './private-cloud.service';

@Component({
  selector: 'jhi-private-cloud-delete-dialog',
  templateUrl: './private-cloud-delete-dialog.component.html'
})
export class PrivateCloudDeleteDialogComponent {
  privateCloud: IPrivateCloud;

  constructor(
    protected privateCloudService: PrivateCloudService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.privateCloudService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'privateCloudListModification',
        content: 'Deleted an privateCloud'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-private-cloud-delete-popup',
  template: ''
})
export class PrivateCloudDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ privateCloud }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrivateCloudDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.privateCloud = privateCloud;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/private-cloud', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/private-cloud', { outlets: { popup: null } }]);
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
