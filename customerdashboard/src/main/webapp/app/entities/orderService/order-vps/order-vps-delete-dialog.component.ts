import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderVps } from 'app/shared/model/orderService/order-vps.model';
import { OrderVpsService } from './order-vps.service';

@Component({
  selector: 'jhi-order-vps-delete-dialog',
  templateUrl: './order-vps-delete-dialog.component.html'
})
export class OrderVpsDeleteDialogComponent {
  orderVps: IOrderVps;

  constructor(protected orderVpsService: OrderVpsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderVpsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderVpsListModification',
        content: 'Deleted an orderVps'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-vps-delete-popup',
  template: ''
})
export class OrderVpsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderVps }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderVpsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderVps = orderVps;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-vps', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-vps', { outlets: { popup: null } }]);
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
